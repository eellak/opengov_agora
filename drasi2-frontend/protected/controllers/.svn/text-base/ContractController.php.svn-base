<?php
class ContractController extends AgoraController
{
	/**
	 * @var string the default layout for the views. Defaults to '//layouts/column2', meaning
	 * using two-column layout. See 'protected/views/layouts/column2.php'.
	 */
	public $layout='//layouts/column2';

	
	/**
	 * @return array action filters
	 */
	public function filters()
	{
		return array(
			'accessControl', // perform access control for CRUD operations
		);
	}

	/**
	 * Specifies the access control rules.
	 * This method is used by the 'accessControl' filter.
	 * @return array access control rules
	 */
	public function accessRules()
	{
		return array(
			
		 array('allow',
		 	 'actions'=>array('index','viewcontract','download','dynamicsigners',
		 	 				'viewContractPayments','cpvcodes','Dynamicforeis',
		 	 				'searchcontract','rss','rss2',),
		 		'users'=>array('*'),
		 ),
		 array('allow', // allow admin user to perform 'admin' and 'delete' actions
				'actions'=>array('viewcontract','createcontract','createbynotice','success','extendContract',
				'changesContract','createContractByProc','cancelcontract'),
				'roles'=>array('authenticated'),
			),
			array('allow', // allow admin user to perform 'admin' and 'delete' actions
				'actions'=>array('viewcontract','createbynotice','createcontract','updatecontract','deletecontract','success',
				'changesContract','extendContract','createContractByProc','cancelcontract'),
				'roles'=>array('admin'),
			),
			array('allow', // allow admin user to perform 'admin' and 'delete' actions
				'actions'=>array('viewcontract','updatecontract','deletecontract','success','cancelcontract'),
				'roles'=>array('superadmin'),
			),
			array('deny',  // deny all users
			   'actions'=>array('delete','viewcontract','createcontract','createbynotice','updatecontract','deletecontract','update'),
				'users'=>array('*'),
			),
		);
	}
	
	/**
	 * Lists all models.
	 */
	public function actionIndex($search="")
	{
		
		$FData = new Contract();
		$search = Yii::app()->request->getParam('search');
		$data = $FData->getContractListTable($search);
		
		//CArrayDataProvider need ID to identification unique column value for ID CGridView
		//So i always have to change the KeyField
		//http://www.yiiframework.com/forum/index.php?/topic/17561-cgridview-and-1-to-1-relationship/
		$dataProvider = new CArrayDataProvider($data, array(
    	'keyField' => 'uniqueDocumentCode'     // Agent primary key here
		));
		$this->render('index',array(	
			'dataProvider'=>$dataProvider,
			));
		
	}
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss1 type. Shows the last 20 appoved Contracts.
	 */
	public function actionRss(){
		$model = new Contract();
		$error=0;
		$search=$name='';
		$data = $model->getContractListTable($search,'','',Yii::app()->params['agora']['rsslimit']);
		
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'contract',
			'version'=>'rss1',
		));	
	}
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss2 type. Shows the last 20 appoved Contracts.
	 */
	public function actionRss2(){
		$model = new Contract();
		$error=0;
		$search=$name='';
		$data = $model->getContractListTable($search,'','',Yii::app()->params['agora']['rsslimit']);
		
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'contract',
			'version'=>'rss2',
		));
		
	}
	
	
/**
	 * Action Search 
	 */
	function actionSearchcontract(){
		
		$model = new Contract();
		$output='';
		$cpv=$finalData='';
		$org=$foreis=$ypoforeis='';
		
		if(Yii::app()->request->isAjaxRequest){
       		$session = Yii::app()->getComponent('session');
       		$title = $session->get('title','');
       		$cpv = $session->get('cvp','');
       		$ada = $session->get('ada','');
       		$org = $session->get('org','');
       		
       	}else{
			if(isset($_POST['Contract']['title'])){
		    	$title = $_POST['Contract']['title'];
		    }else $title='';
		    $model->title=$title;      
		    $session = Yii::app()->getComponent('session');
		    $session->add('title',$title);

		    //foreis - ypoforeis 
		    if (Yii::app()->user->isGuest){
			    if(isset($_POST['Contract']['foreis'])){
			    	$foreis = $_POST['Contract']['foreis'];
			    }else $foreis='';
			    
			     if(isset($_POST['Contract']['ypoforeis'])){
			    	$ypoforeis = $_POST['Contract']['ypoforeis'];
			    }else $ypoforeis='';
		    
			    $session = Yii::app()->getComponent('session');
			    if($foreis==0 && ($ypoforeis==0 || $ypoforeis=='----')){
			    	$org='';
			    $session->add('org',0);
			    }else if($ypoforeis==0 || $ypoforeis=='----'){
			    		$session->add('org',$foreis);
			    		$org=$foreis;
			    }
			    else {
			    	$session->add('org',$ypoforeis);
			    	$org=$ypoforeis;
			    }
		    }
		    if(isset($_POST['Contract']['cpvsid']) && $_POST['Contract']['cpvsid']!=''){
				$cpv=$_POST['Contract']['cpvsid'];
					
			}else $cpv='';
			$model->cpvsid=$cpv;
			$session = Yii::app()->getComponent('session');
		    $session->add('cpv',$cpv);
			
		    if(isset($_POST['Contract']['documentUrl'])){
		    	$ada = $_POST['Contract']['documentUrl'];
		    }else $ada='';
		    $session = Yii::app()->getComponent('session');
		    $session->add('ada',$ada);
		    $model->documentUrl=$ada;
       }
			
		$data = $model->getRfMultipleListTable($ada,$title,$cpv,$org);
		$dataProvider = new CArrayDataProvider($data, array(
    		'keyField' => 'title'     // Agent primary key here
		));
		
		$cost=0;
		if(is_array($data) && count($data)>0){
			for($i=0;$i<count($data) && $data!=404 && $data!=403 && $data!=-1;$i++){
				$data[$i]['actionPermitted']=$model->isPermitted($data[$i]['actionPermitted']);
				//$cost = $cost + $data[$i]['totalCostBeforeVAT'];
			}
		}//else 
			//$cost= $data[0]['totalCostBeforeVAT'];
		//	$fm = new CNumberFormatter(Yii::app()->getLocale());
		//	$cost =$fm->formatCurrency($cost,'EUR');
		$this->render('search',array(	
				'dataProvider'=>$dataProvider,
				'cost'=>$cost,
				'model'=>$model
			));
	
	}
	

	
	/**
	 * Displays a particular model.
	 * @param integer $id the ID of the model to be displayed
	 */
	public function actionViewContract()
	{
		
		//$ContractItems =Contractitem::loadModel( $_GET['uniqueDocumentCode']);
		$test="";
		$uniqueDocumentCode = $_GET['uniqueDocumentCode'];
		//if(Yii::app()->user->checkAccess('viewcontract')){
		
			$data = $this->loadModel($uniqueDocumentCode);
			if(isset($data->uniqueDocumentCode) && !is_null($data->uniqueDocumentCode)) {

			//if there is no Unit.
			$data->OrganizationIdRefUnits=	Units::item('Units',$data->OrganizationIdRefUnits,$data->OrganizationIdRef);	
			$ContractItem = new Contractitem();
			$ContractData = $ContractItem->findByPk($uniqueDocumentCode,'contractsitem');
			$Signers = new Signers();
			$SignersData = $Signers->findByPk($uniqueDocumentCode,'contractsitem');
			for($i=0;$i<count($data->signers);$i++)
				$test .=Units::item('Signers',$data->signers[$i],$data->OrganizationIdRef ) ."<br/>";
				$data->signers  = $test;
				$data->contractType =Lookup::item('contractType',$data->contractType);
				$data->commissionCriteria =Lookup::item('commissionCriteria',$data->commissionCriteria);
				$data->contractingAuthority = Lookup::item('contracting_authority', $data->contractingAuthority);
				$data->awardProcedure = Lookup::item('award_procedure', $data->awardProcedure);				
				$data->diavgeiaPublished = (strcmp($data->diavgeiaPublished,"true"))?Yii::t('form','DiavgeiaPNo'):Yii::t('form','DiavgeiaPYes');
				$data->document= "<a href='index.php?r=contract/contractdownload&uniqueDocumentCode=".$data->uniqueDocumentCode.">Download</a>";
			$this->render('viewcontract',array(
				'model'=>$data,
				'ContractData'=>$ContractData,
				'SignersData' =>$SignersData,
			));
			}else 				
				$this->render('error',array('code'=>Yii::t('contract','noview'),'message'=>Yii::t('contract','noviewmessage')));
			
			
	
	}
	
	public function actionviewContractPayments($uniqueDocumentCode){
		$test="";
		$uniqueDocumentCode = $_GET['uniqueDocumentCode'];
		
		$data = $this->loadModel($uniqueDocumentCode);
		if(isset($data->uniqueDocumentCode) && !is_null($data->uniqueDocumentCode)) {
			//if there is no Unit.
			$data->OrganizationIdRefUnits=	Units::item('Units',$data->OrganizationIdRefUnits,$data->OrganizationIdRef);	
			$ContractItem = new Contractitem();
			$ContractData = $ContractItem->findByPk($uniqueDocumentCode,'contractsitem');
			$Payments = new Payments();
			$Payments = $Payments->findByPkByContract($uniqueDocumentCode);
			
			$ids = array();
			for($i=0;$i<count($Payments);$i++){
				$ids[$i] = $Payments[$i]['documentUrl'];
			}
			
			$ContractItemPayments = new Contractitempayments();
			$ContractItemPayments = $ContractItemPayments->findByMultiplePkPayments($ids);
			$Signers = new Signers();
			$SignersData = $Signers->findByPk($uniqueDocumentCode,'contractsitem');
			for($i=0;$i<count($data->signers);$i++)
				$test .=Units::item('Signers',$data->signers[$i],$data->OrganizationIdRef ) ."<br/>";
				$data->signers  = $test;
				$data->contractType =Lookup::item('contractType',$data->contractType);
				$data->commissionCriteria =Lookup::item('commissionCriteria',$data->commissionCriteria);
				$data->contractingAuthority = Lookup::item('contracting_authority', $data->contractingAuthority);
				$data->awardProcedure = Lookup::item('award_procedure', $data->awardProcedure);				
				//$data->diavgeiaPublished = (strcmp($data->diavgeiaPublished,"true"))?Yii::t('form','DiavgeiaPNo'):Yii::t('form','DiavgeiaPYes');
				$data->document= "<a href='index.php?r=contract/contractdownload&uniqueDocumentCode=".$data->uniqueDocumentCode.">Download</a>";
			
			$this->render('contractPayments',array(
				'model'=>$data,
				'ContractData'=>$ContractData,
				'SignersData' =>$SignersData,
				'ContractItemPayments'=>$ContractItemPayments,
			));
			
			}else 				
				$this->render('error',array('code'=>Yii::t('contract','noview'),'message'=>Yii::t('contract','noviewmessage')));
			
	}
	/**
	 * Is the main function called to create a  contract.
	 */

	public function actionCreateContract(){
		
		$model=new Contract();
		$contractitem=new Contractitem();
		$contractitemmanager=new ContractItemManager();
		$signersmanager=new SignersManager();
		$model->scenario='createcontract';
		
		// Uncomment the following line if AJAX validation is needed
		$this->performAjaxValidation($model);
		
		if(isset($_POST['Contract']))
		{
			$model->attributes=$_POST['Contract'];
			if(isset($_POST['Contractitem']))
				$contractitemmanager->manage($_POST['Contractitem']);
			else
				Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
			if(isset($_POST['Signers']))
				$signersmanager->manage($_POST['Signers']);
			else
				Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
			
			$valid = $model->validate();
			$valid = $contractitemmanager->validate($model) && $valid;
			$valid = $signersmanager->validate($model) && $valid;
			//if(isset($_POST)	
			
			if($valid){
				$data = $model->saveToApi();				
				if(is_array($data) && !isset($data[0]['error'])){
					$this->successContract('contractSuccess', array('model'=>$model,'documentUrl'=>$data[0]['id'],'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'create'));
				}else {
					
					$errors = new ApiErrorHandling('contract');
					for($i=0;$i<count($data[0]['error']);$i++){
						
						$model->addError($errors->NameError($data[0]['error'][$i]),$errors->DescriptionError($data[0]['error'][$i]));	
					}
				}	
				
			}
		}

		$this->render('create',array(
			'model'=>$model,
		    'contractitemmanager'=>$contractitemmanager,
		    'modelname'=>'contract',
			'signersmanager'=>$signersmanager,
		));
	}
	
	/**
	 * Create a new contract by Using procurement Ids.
	 * It is a two step form. 
	 * At first step the user selects the ids of procurements.
	 * @param integer $step the number of Step
	 */
	public function actioncreateContractByProc($step='')
	{
		
		if(!Yii::app()->user->isGuest){
			$step = Yii::app()->request->getParam('step');
			$proc = Yii::app()->request->getParam('proc');
			//Check if there are Procurements
			$Item = new Procurement();
			$numOfProcurements = $Item->GetNumberOfProcurement();
			$numOfNotices=0;
	
			$model = new Contract();
			$contractitem=new Contractitem();
			$contractitemmanager=new ContractItemManager();
			$signersmanager=new SignersManager();
			$model->scenario='createcontractbyproc';
			
			//step number
			$step = $step ? $step : 1;
			
			if($step==1){
				if(isset($_POST['Contract']['procurements']))
				$procurements = $_POST['Contract']['procurements'];
		
			}else if($step=='2' && $_POST['Contract']['procurements']=='' && $model->procurements==''){
				$step=1;
				$model->addError('procurements',Yii::t('contract','procurementIds'));
			}else if ($step=='2'){
				$procurements = $_POST['Contract']['procurements'];
				$model->attributes=$_POST['Contract'];

				
				$model->attributes=$_POST['Contract']['procurements'];
				//Create table with the selected procurementsIDs
 				$ProcIds = explode(";", $procurements);
				$test=array();
				for($i=0;$i<count($ProcIds);$i++){
					$key = in_array($ProcIds[$i], $test); 
					if($key) {
						$step=1;
						$model->addError('procurements',Yii::t('contract','procdescmultiple')."&nbsp;<u>
							<strong>". $ProcIds[$i]."</strong></u>");
					}
					
					$data = $Item->findByPk($ProcIds[$i]);
					$test[$i]= $ProcIds[$i];
					if($ProcIds[$i]!='')
					if($data->cancelled=='true'){						
							$step=1;
							$model->addError('procurements',Yii::t('contract','procdesc')."&nbsp;<u>
									<strong>".  $ProcIds[$i]."</strong></u>&nbsp;
									".Yii::t('procurement','cancelledmessage'));
						}
				}
				$contractModel = $contractitem->findByMultiplePk($ProcIds,'procurement');
				//if the procurementIds doesnt exist then 
				//draw errors.
				
				//echo $contractModel->dateSigned[0];
				if(!isset($contractModel->ContractItemId) ) {
					$step=1;
					$model->addError('procurements',Yii::t('contract','procurementIds'));
					for($i=0;$i<count($contractModel);$i++)
						$model->addError('procurements',Yii::t('contract','procdesc')."&nbsp;<u>
							<strong>". $contractModel[$i]['documentUrl']."</strong></u>&nbsp;".Yii::t('contract','procid'.$contractModel[$i]['error']));
				}else{
					
					
					for($i=0;$i<count($contractModel['quantity']);$i++){
						
						if($contractModel->approvesRequest[$i]==''){						
							$step=1;
							$model->addError('procurements',Yii::t('contract','procdesc')."&nbsp;<u>
									<strong>". $contractModel->ContractItemId[$i]."</strong></u>&nbsp;
									".Yii::t('contract','approvesNo'));
						}
					}
					if($step!=1){
						$contractitemmanager=ContractitemManager::load($contractModel);
						$procurements = $model->procurements;
						$awards = $contractitem->getAwardProcedure($ProcIds,'procurement');
						$model->awardProcedure=$awards;
						
					
					}
					}
				
			}else if($step==3){
				
				
				if(isset($_POST['Contract']) && isset($_POST['Contract']['title'])){
					$this->performAjaxValidation($model);	
					$model->attributes=$_POST['Contract'];
				
				if(isset($_POST['Contractitem']))
					$contractitemmanager->manage($_POST['Contractitem']);
				else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
			
				if(isset($_POST['Signers']))
					$signersmanager->manage($_POST['Signers']);
				else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
			
					$valid = $model->validate();
					$valid = $contractitemmanager->validate($model) && $valid;
					$valid = $signersmanager->validate($model) && $valid;
					
				
					if($valid){
						$data = $model->saveToApi();
						
						if(is_array($data) && !isset($data[0]['error'])){
							$this->successContract('contractSuccess', array('model'=>$model,'documentUrl'=>$data[0]['id'],'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'create'));
						}else {
							$errors = new ApiErrorHandling('contract');
							for($i=0;$i<count($data[0]['error']);$i++){
								$model->addError($errors->NameError($data[0]['error'][$i]),$errors->DescriptionError($data[0]['error'][$i]));	
							}
						}
					}				
				}
			}//end step 3
			
			$this->render('createByProc',array(
				'model'=>$model,
 			    'contractitemmanager'=>$contractitemmanager,
				'signersmanager'=>$signersmanager,
				'step'=>$step,
				'Mainaction'=>'createByProc',
				'numOfProcurements' =>$numOfProcurements,
			    'modelname'=>'contract',
				'numOfNotices'=>$numOfNotices,
			
			));
		}else //no permissions to do the action  			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
	}

	
/**
	 * Create a new contract by Using a notice Ids.
	 * It is a two step form. 
	 * At first step the user selects the ids of procurements.
	 * @param integer $step the number of Step
	 */
	public function actioncreateContractByNotice($step='')
	{
		
		if(!Yii::app()->user->isGuest){
			$step = Yii::app()->request->getParam('step');
			$proc = Yii::app()->request->getParam('proc');
			//Check if there are Procurements
			$numOfProcurements = 0;
			$Notices = new Rfp();
			$numOfNotices = $Notices->GetNumberOfRfp();
	
			$model = new Contract();
			$contractitem=new Contractitem();
			$contractitemmanager=new ContractItemManager();
			$signersmanager=new SignersManager();
			$model->scenario='createcontractbynotice';
			
			//step number
			$step = $step ? $step : 1;
			
			if($step==1){
				if(isset($_POST['Contract']['noticeADA']))
				$noticeADA = $_POST['Contract']['noticeADA'];
		
			}else if($step=='2' && $_POST['Contract']['noticeADA']=='' && $model->noticeADA==''){
				$step=1;
				$model->addError('noticeADA',Yii::t('contract','noticeADAerror'));
			}else if ($step=='2'){
				$noticeADA = $_POST['Contract']['noticeADA'];
				$model->attributes=$_POST['Contract'];
			
		
				$contractModel = $contractitem->findByPk($noticeADA,'rfpitem');
				//print_r($contractModel);
				//if the procurementIds doesnt exist then 
				//draw errors.
				if($contractModel->quantity==''){
					$step=1;
					$model->addError('noticeADA',Yii::t('contract','noticenoexists')."&nbsp;<u>
							<strong>". $noticeADA."</strong></u>");
				}else{
					$RFP = new Rfp();
					$RfpDATA = $RFP->getRFPItem($noticeADA);
				
				
					if($RfpDATA==404){
						$step=1;
						$model->addError('noticeADA',Yii::t('contract','noticenoexists')."&nbsp;<u>
								<strong>". $noticeADA."</strong></u>");
					}
					if($RfpDATA[0]['cancelled']=='true'){						
							$step=1;
							$model->addError('noticeADA',Yii::t('contract','rfpdesc')."&nbsp;<u>
									<strong>". $noticeADA."</strong></u>&nbsp;
									".Yii::t('contract','cancelledmessage'));
					}
					$model->awardProcedure=$RfpDATA[0]['awardProcedure'];
					//echo $contractModel->dateSigned[0];
					if(!isset($contractModel->ContractItemId) ) {
						$step=1;
						$model->addError('procurements',Yii::t('contract','procurementIds'));
						for($i=0;$i<count($contractModel);$i++)
							$model->addError('procurements',Yii::t('contract','procdesc')."&nbsp;<u>
								<strong>". $contractModel[$i]['documentUrl']."</strong></u>&nbsp;".Yii::t('contract','procid'.$contractModel[$i]['error']));
					}else{
						if($step!=1){
								$contractitemmanager=ContractitemManager::load($contractModel);
								$noticeADA=$model->noticeADA;
						}
					}
				}
				
			}else if($step==3){
				
				
				if(isset($_POST['Contract']) && isset($_POST['Contract']['title'])){
					$this->performAjaxValidation($model);	
					$model->attributes=$_POST['Contract'];
				
				if(isset($_POST['Contractitem']))
					$contractitemmanager->manage($_POST['Contractitem']);
				else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
			
				if(isset($_POST['Signers']))
					$signersmanager->manage($_POST['Signers']);
				else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
			
					
					$valid = $model->validate();
					$valid = $contractitemmanager->validate($model) && $valid;
					$valid = $signersmanager->validate($model) && $valid;
					
					
					if($valid){
						$data = $model->saveToApi();
						
						if(is_array($data) && !isset($data[0]['error'])){
							$this->successContract('contractSuccess', array('model'=>$model,'documentUrl'=>$data[0]['id'],'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'create'));
						}else {
							$errors = new ApiErrorHandling('contract');
							for($i=0;$i<count($data[0]['error']);$i++){
								$model->addError($errors->NameError($data[0]['error'][$i]),$errors->DescriptionError($data[0]['error'][$i]));	
							}
						}
					}			
				}
			}//end step 3
			
			$this->render('createByProc',array(
				'model'=>$model,
 			    'contractitemmanager'=>$contractitemmanager,
				'signersmanager'=>$signersmanager,
				'step'=>$step,
				'Mainaction'=>'createByNotice',
				'numOfProcurements' =>$numOfProcurements,
			    'modelname'=>'contract',
				'numOfNotices'=>$numOfNotices,
			
			));
		}else //no permissions to do the action  			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
	}
	/**
	 * Update a selected contract.
	 */
	public function actionUpdateContract()
	{
		if(!Yii::app()->user->isGuest){

		$id = Yii::app()->request->getParam('uniqueDocumentCode');	
		$model=$this->loadModel($id);
		//print_r($model);
		$model->scenario="update";
		$model->setScenario("update");

		if( (!$model->actionPermitted))
			$this->render('error',array('code'=>Yii::t('contract','noAction'),'message'=>Yii::t('contract','noActionmessage')));
		else if($model->cancelled=='true')
			$this->render('notice',array('code'=>Yii::t('contract','cancelled'),'message'=>Yii::t('contract','cancelledmessage')));
		else{
		
			if(!is_null($model->uniqueDocumentCode)) {
			
			$contractItem = new Contractitem();
			$contractModel = $contractItem->findByPk($id,'contractsitem');
			$contractitemmanager=ContractItemManager::load($contractModel);
			//$_POST['Contract']['untilexists']=$model->untilexists;
			$signers = new Signers();
			$signersModel = $signers->findByPk($id,'contractsitem');
			$signersmanager=SignersManager::load($signersModel);
			
			//echo $model->procurements; 
			// Uncomment the following line if AJAX validation is needed
			$this->performAjaxValidation($model);
			
			if(isset($_POST['Contract']) && isset($_POST['Contract']['title']))
			{
				
				$model->attributes=$_POST['Contract'];
				if(isset($_POST['Contract']['procurements']))
				$model->procurements = $_POST['Contract']['procurements'];
				
				$valid = $model->validate();
				
				if(isset($_POST['Contractitem'])){
					$contractitemmanager->manage($_POST['Contractitem']);
				}else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
				if(isset($_POST['Signers']))
					$signersmanager->manage($_POST['Signers']);
				else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
				
				$valid = $contractitemmanager->validate($model) && $valid;
				$valid = $signersmanager->validate($model) && $valid;			
				
				if($valid){
					$data = $model->saveToApi('update');
					if(is_array($data) && !isset($data[0]['error'])){
						$this->successContract('contractSuccess', array('model'=>$model,'documentUrl'=>$data[0]['id'],'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'update'));
					}else {
						$errors = new ApiErrorHandling('contract');						
						for($i=0;$i<count($data[0]['error']);$i++){
							$model->addError($errors->NameError($data[0]['error'][$i]),$errors->DescriptionError($data[0]['error'][$i]));	
						}
					}
				
				}
			}
			$this->render('update',array(
				'model'=>$model,
			    'contractitemmanager'=>$contractitemmanager,
				'signersmanager'=>$signersmanager,
				'type'=>'contract',
			    'modelname'=>'contract',
			
			
			));
			}else
			$this->render('error',array('code'=>Yii::t('contract','noview'),'message'=>Yii::t('contract','noviewmessage')));
			}
		}else 
	Yii::app()->request->redirect(Yii::app()->user->returnUrl);	
	}
	
	/**
	 * This function extends a contract.
	 * The dateSigned, since, until must have one more rule
	 * They must be greater than the old ones. So i have to 
	 * add a new rule comparing model values with form values. 
	 * This is done through the onBeforeValidationAction. 
	 * 
	 * Apart from the new rules another characteristic is that
	 * some values must be disabled. The values of the attributes
	 * that are disabled are passed through hidden values. It is
	 * necessary to use hidden values otherwise the model has 
	 * validation errors. 
	 * 
	 * For dates comparison and validation we use th SCompareDates component.
	 * 
	 * We have to check the 
	 * a) user permissions, b)the params . 
	 */
	function actionExtendContract(){
		
		if(!Yii::app()->user->isGuest){
			$id = Yii::app()->request->getParam('uniqueDocumentCode');	
			
			$model=$this->loadModel($id);
			if(!isset($model)) return;
			if($model->cancelled=='true')
				$this->render('notice',array('code'=>Yii::t('contract','cancelled'),'message'=>Yii::t('contract','cancelledmessage')));
			else if(isset($model->title)) {
			$model->scenario="extend";
			$model->setScenario("extend");
			$procurements = isset($model->procurements)?$model->procurements:'';
			
			$contractItem = new Contractitem();
			$contractModel = $contractItem->findByPk($id,'contractsitem');
			$contractitemmanager=ContractItemManager::load($contractModel);
			$signers = new Signers();
			$signersModel = $signers->findByPk($id,'contractsitem');
			$signersmanager=SignersManager::load($signersModel);
			// Uncomment the following line if AJAX validation is needed
			//$this->performAjaxValidation($model);
		
			if(isset($_POST['Contract']))
			{
				$model->attributes=$_POST['Contract'];
				$valid = $model->validate();
				$model->attributes=$_POST['Contract'];
				if(isset($_POST['Contractitem'])){
					$contractitemmanager->manage($_POST['Contractitem']);
				}else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
				if(isset($_POST['Signers']))
					$signersmanager->manage($_POST['Signers']);
				else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
				
				$valid = $contractitemmanager->validate($model) && $valid;
				$valid = $signersmanager->validate($model) && $valid;
				if($valid){
					$data = $model->saveToApi('extend');
							
					if(is_array($data) && !isset($data[0]['error'])){
						$this->successContract('contractSuccess', array('model'=>$model,'documentUrl'=>$data[0]['id'],'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'extend'));
					}else {
							$errors = new ApiErrorHandling('contract');
							for($i=0;$i<count($data[0]['error']);$i++){
								$model->addError($errors->NameError($data[0]['error'][$i]),$errors->DescriptionError($data[0]['error'][$i]));	
							}
					}	
				}
			}
			$this->render('extend',array(
				'model'=>$model,
			    'contractitemmanager'=>$contractitemmanager,
				'signersmanager'=>$signersmanager,
				'Mainaction'=>'extend',
			   'modelname'=>'contract',
				'type'=>'contract',
			));
			}else
				$this->render('error',array('code'=>Yii::t('contract','noview'),'message'=>Yii::t('contract','noviewmessage')));
		}else 
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);		
		
	}
/**
	 * This function changes a contract.
	 * The dateSigned, since, until must have one more rule
	 * They must be greater than the old ones. So i have to 
	 * add a new rule comparing model values with form values. 
	 * This is done through the onBeforeValidationAction. 
	 * 
	 * Apart from the new rules another characteristic is that
	 * some values must be disabled. The values of the attributes
	 * that are disabled are passed through hidden values. It is
	 * necessary to use hidden values otherwise the model has 
	 * validation errors. 
	 * 
	 * For dates comparison and validation we use th SCompareDates component.
	 * 
	 * We have to check the 
	 * a) user permissions, b)the params . 
	 */
	function actionChangesContract(){
		
		if(!Yii::app()->user->isGuest){
			$id = Yii::app()->request->getParam('uniqueDocumentCode');	
			
			$model=$this->loadModel($id);
			if(!isset($model)) return;
			if($model->cancelled=='true')
				$this->render('notice',array('code'=>Yii::t('contract','cancelled'),'message'=>Yii::t('contract','cancelledmessage')));
			else if(isset($model->title)) {
			$model->scenario="changes";
			$model->setScenario("changes");
			$procurements = isset($model->procurements)?$model->procurements:'';
			
			$contractItem = new Contractitem();
			$contractModel = $contractItem->findByPk($id,'contractsitem');
			$contractitemmanager=ContractItemManager::load($contractModel);
			$signers = new Signers();
			$signersModel = $signers->findByPk($id,'contractsitem');
			$signersmanager=SignersManager::load($signersModel);
			// Uncomment the following line if AJAX validation is needed
			//$this->performAjaxValidation($model);
		
			if(isset($_POST['Contract']))
			{
				$model->attributes=$_POST['Contract'];
				$valid = $model->validate();
				$model->attributes=$_POST['Contract'];
				if(isset($_POST['Contractitem'])){
					$contractitemmanager->manage($_POST['Contractitem']);
				}else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
				if(isset($_POST['Signers']))
					$signersmanager->manage($_POST['Signers']);
				else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
				
				$valid = $contractitemmanager->validate($model) && $valid;
				$valid = $signersmanager->validate($model) && $valid;
				if($valid){
					$data = $model->saveToApi('changes');
							
					if(is_array($data) && !isset($data[0]['error'])){
						$this->successContract('contractSuccess', array('model'=>$model,'documentUrl'=>$data[0]['id'],
						'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'changes'));
					}else {
							$errors = new ApiErrorHandling('contract');
							for($i=0;$i<count($data[0]['error']);$i++){
								$model->addError($errors->NameError($data[0]['error'][$i]),$errors->DescriptionError($data[0]['error'][$i]));	
							}
					}	
				}
			}
			$this->render('changes',array(
				'model'=>$model,
			    'contractitemmanager'=>$contractitemmanager,
				'signersmanager'=>$signersmanager,
				'Mainaction'=>'changes',
			   'modelname'=>'contract',
				'type'=>'contract',
			));
			}else
				$this->render('error',array('code'=>Yii::t('contract','noview'),'message'=>Yii::t('contract','noviewmessage')));
		}else 
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);		
		
	}
	
		public function actionContractDownload(){
			
			$docURI =$_GET['uniqueDocumentCode'];	
			
			$file = "contract-".$docURI.".pdf";
			
			//Connect to ApiConnector
			//sets the config table for the http request. 
			$config['apirequest'] ='contractDownload';
			$config['documentid'] =$docURI;
			
			//send a curl request for the document 
			$api = new Apiconnector($config);
			$output = $api->makeRequest();
			
			$download = new filedownload($output,"files",$file);
		}
	/**
	 * Deletes a particular model.
	 * If deletion is successful, the browser will be redirected to the 'success deletion' page.
	 * While deleting the following cases may appear
	 * a) Successful deletion --> $delete=1
	 * b) Unauthorized deletion --> $delete=403
	 * c) Trying to delete a procurement that doesnt exist --> $delete=404
	 * d) Bad request -->400 
	 * @param integer $uniqueDocumentCode the ID of the model to be deleted
	 */
	public function actionDeleteContract($uniqueDocumentCode)
	{
		if(!Yii::app()->user->isGuest){
			$model=new Contract();
			$model->scenario='delete';
			$model->findByPk($uniqueDocumentCode);
			
			if($model->cancelled=='true')
				$this->render('notice',array('code'=>Yii::t('contract','cancelled'),'message'=>Yii::t('contract','cancelledmessage')));
		
			else if($model->title){
				if(isset($_POST['Contract'])){
			
				$model->attributes=$_POST['Contract'];
				
				if($_POST['Contract']['Deldescription']==''){
					$valid = $model->validate();
				}else{
					$config['reason'] = $_POST['Contract']['Deldescription'];
					$apiDelete = new Apiconnector($config);
					$delete = $apiDelete->delete(urlencode($uniqueDocumentCode),'contract');

					if($delete==1){
						$this->success('contractSuccess', array('model'=>$model,'documentUrl'=>$uniqueDocumentCode,
							   'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,
							   'deldescription'=>$_POST['Contract']['Deldescription'],'action'=>'delete'));
					}else
						$this->errorProc('errorForm',$delete,Yii::t('form','delete'.$delete),'contract');
						//$model->addError('form',Yii::t('form','delete'.$delete));	
				
				}//there is a reason
			}//post is send
			$this->render('_delete', array('model'=>$model));
			}
		}else 			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
	}		
	/**
	 * Cancels a particular model.
	 * If cancelation is successful, the browser will be redirected to the 'success cancelation' page.
	 * While canceling the following cases may appear
	 * a) Successful cancelation --> $cancel=1
	 * b) Unauthorized cancelation --> $cancel=403
	 * c) Trying to cancel an rfp that doesnt exist --> $cancel=404
	 * d) Bad request -->400 
	 * @param integer $uniqueDocumentCode the ID of the model to be deleted
	 */
	public function actionCancelContract($uniqueDocumentCode)
	{
		if(!Yii::app()->user->isGuest){
			$model=new Contract();
			$model->scenario='cancelcontract';
			$model->findByPk($uniqueDocumentCode);
			
			if ($model->cancelled=='true')
				$this->render('notice',array('code'=>Yii::t('procurement','cancelled'),'message'=>Yii::t('procurement','cancelledmessage')));
			else if($model->title){
			if(isset($_POST['Contract'])){
				$model->attributes=$_POST['Contract'];
				
				if($_POST['Contract']['Deldescription']==''){
					$valid = $model->validate();
				}else{
					$config['username']=Yii::app()->user->UserName;
					$config['password']=Yii::app()->user->password;
					$config['apirequest'] ='contractcancel';
					$config['documentid'] =$uniqueDocumentCode;
					$ADACancel = $_POST['Contract']['ADACancel'];
					$delCancel = $_POST['Contract']['Deldescription'] ."---".$ADACancel."---";
					$config['reason'] = $delCancel;
					$config['cancellationType'] = $_POST['Contract']['cancellationType'];
					$apiDelete = new Apiconnector($config);
					$cancel = $apiDelete->cancel($uniqueDocumentCode,'contract');

					if($cancel==1){
						$this->success('contractSuccess', array('model'=>$model,'documentUrl'=>$uniqueDocumentCode,
							   'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,
							   'deldescription'=>$_POST['Contract']['Deldescription'],'action'=>'cancel'));
					}else
						$this->errorProc('errorForm',$cancel,Yii::t('form','cancel'.$cancel));
									
				}//there is a reason
			}//post is send
			$this->render('_cancel', array('model'=>$model));
			}
		}else 			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
	}		
	
	/**
	 * 
	 * Loads the data of the element with the selected id 
	 * @param the document id $id
	 */
	public function loadModel($id){

		$model=new Contract();
		$model=$model->findByPk($id);

		if($model===null)
			throw new CHttpException(404,'The requested page does not exist.');
		return $model;
	}
	

	
	
	/**
	 * Fetches the list of contracts in a model
	 * It connects to the Apiconnector and fetches the xml (component Apiconnector)
	 * The xml is then parsed (xpath) via the contartapi data and are stored 
	 * in a model (component Contractapidata.php)
	 * @return a model with the short contract list data 
	 */
	public function getContractList(){
		
		//Connect to ApiConnector
		//sets the config table for the http request. 
		$config['apirequest'] ='contractsshort';
		
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		if($output==500) return -1;
		
		$Results = new Contractapidata();
		$model = new Contract();
		$model = $Results->getShortList($output);
		
		//Fetch Data to model
		return $model;
		
	}
	/**
	 * Fetches the data of a contract in an array
	 * It connects to the Apiconnector and fetches the xml (component Apiconnector)
	 * The xml is then parsed (xpath) via the contartapi data and the data are stored 
	 * in a table (component Contractapidata.php)
	 * @return an array with the contract data 
	 */
	public function getContractItem($id){
		
		//Connect to ApiConnector
		//sets the config table for the http request. 
		$config['apirequest'] ='contractsitem';
		$config['documentid'] = $id;
				
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		
		$Results = new Contractapidata();
		$Results = $Results->getItemData($output);
		
		return $Results;
		
	}
	
	/**
	 * Performs the AJAX validation.
	 * @param CModel the model to be validated
	 */
	protected function performAjaxValidation($model)
	{
		if (Yii::app()->request->isAjaxRequest && isset($_POST['ajax']))
	  {
	    $class = get_class($model);
	    if (isset($_POST['attributes']) && is_array($_POST['attributes'][$class]))
	      $attributes = $_POST['attributes'][$class];
	    else
	      $attributes = null;
	    echo CActiveForm::validate($model, $attributes);
	    Yii::app()->end();
	  }
			
		/*if(isset($_POST['ajax']) && $_POST['ajax']==='procurement-form')
		{
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}*/
	}
	
	/**
	 * This is the action to handle external exceptions.
	 */
	public function actionError()
	{
	    echo Yii::app()->errorHandler->error ."<br/>";
	    if($error=Yii::app()->errorHandler->error)
	    {
	    	if(Yii::app()->request->isAjaxRequest)
	    		echo $error['message'];
	    	else
	        	$this->render('error', $error);
	    }
	}
	
	/**
	 * Displays a success page.
	 * The success page content is specified via the flash data '_success_'
	 * which is generated by {@link Controller::success()} method.
	 * If the flash data does not exist, it will redirect the browser to the homepage.
	 */
	public function actionSuccess()
	{
	    if (!Yii::app()->user->hasFlash('_success_'))
	        $this->redirect(Yii::app()->homeUrl);
	    $data = Yii::app()->user->getFlash('_success_');
	    $view = $data['_view_'];
	    unset($data['_view_']);
	    $this->render($view,array('data'=>$data));
	}
	
	/**
	 * This function is responsible for handling the 
	 * errors of the requests. If the action is not available
	 * then you should redirect to an error page.
	 * @param string $actionId
	 */
	public function missingAction($actionId)
	{
	    switch ($actionId)
	    {
	        default: // you can redirect or throw an exception there or call parent::missingAction($actionId).
				$this->render('error',array('code'=>Yii::t('form','PageNotFound'),'message'=>Yii::t('form','PageNotFoundDetails')));
	        	        	
	    }
	}
	
	
	/**
	 * Dependent drop down menu Units - Signers. 
	 * This function is used to generate the second level 
	 * drop down menu of Signers. The user selects a unit 
	 * from the dropdown menu and an ajax request is made 
	 * to create the second level menu. It is used in _form.php.
	 */
	public function actionDynamicsigners(){
		$OrgNewRef = $_POST['Contract']['OrganizationIdRefUnits'];
		$data= Units::itemsSigners('Signers',$OrgNewRef);
		
    	foreach($data as $value=>$name)
			echo CHtml::tag('option',array('value'=>$value),CHtml::encode($name),true);
	}
	
		
	/**
	 * Dependent drop down menu foreis - ypoforeis. 
	 * This function is used to generate the second level 
	 * drop down menu of ypoforeis. The user selects a unit 
	 * from the dropdown menu and an ajax request is made 
	 * to create the second level menu. It is used in _form.php.
	 */
	public function actionDynamicforeis(){
		$foreis = $_POST['Rfp']['foreis'];
		$data= ODE::items('withid',$foreis);
		
		foreach($data as $value=>$name)
			echo CHtml::tag('option',array('value'=>$value),CHtml::encode($name),true);
	}
	
}
?>