<?php
class RfpController extends AgoraController
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
		 	 'actions'=>array('index','viewrfp','download','dynamicsigners','searchrfp','cpvcodes',
		 	 				 'Dynamicforeis','Check','rss','rss2'),
		 		'users'=>array('*'),
		 ),
		 array('allow', // allow admin user to perform 'admin' and 'delete' actions
				'actions'=>array('viewrfp','createrfp','success','searchrfp','cpvcodes','cancelrfp'),
				'roles'=>array('authenticated'),
			),
		
			array('allow', // allow admin user to perform 'admin' and 'delete' actions
				'actions'=>array('viewrfp','createrfp','updaterfp','deleterfp','success','searchrfp','cpvcodes','cancelrfp'),
				'roles'=>array('admin'),
			),
			array('allow', // allow admin user to perform 'admin' and 'delete' actions
				'actions'=>array('viewrfp','updaterfp','deleterfp','success','searchrfp','cancelrfp'),
				'roles'=>array('superadmin'),
			),
			array('deny',  // deny all users
			   'actions'=>array('deleterfp','viewrfp','createrfp','updaterfp','updaterfp'),
				'users'=>array('*'),
			),
		);
	}
	
	/**
	 * Lists all rfp.
	 */
	public function actionIndex($search="")
	{
		$search = Yii::app()->request->getParam('search');
		$Rfp = new Rfp();
		$data = $Rfp->getRfpListTable($search);
		//CArrayDataProvider need ID to identification unique column value for ID CGridView
		//So i always have to change the KeyField
		//http://www.yiiframework.com/forum/index.php?/topic/17561-cgridview-and-1-to-1-relationship/
		$dataProvider = new CArrayDataProvider($data, array(
    	'keyField' => 'title'     // Agent primary key here
		));
		
		$this->render('index',array(	
			'dataProvider'=>$dataProvider,
			));
		
	}
	
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss1 type. Shows the last 20 appoved procurements.
	 */
	public function actionRss(){
		$model = new Rfp();
		$error=0;
		$search=$name='';
		$data = $model->getRfpListTable($search,'',Yii::app()->params['agora']['rsslimit']);
		//print_r($data);
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'rfp',
			'version'=>'rss1',
		));	
	}
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss2 type. Shows the last 20 appoved procurements.
	 */
	public function actionRss2(){
		$model = new Rfp();
		$error=0;
		$search=$name='';
		$data = $model->getRfpListTable($search,'',Yii::app()->params['agora']['rsslimit']);
		//print_r($data);
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'rfp',
			'version'=>'rss2',
		));	
		
	}
	/**
	 * Displays a particular model.
	 * @param integer $id the ID of the model to be displayed
	 */
	public function actionViewRfp()
	{
		$test="";
		$uniqueDocumentCode = $_GET['uniqueDocumentCode'];
			
		$data = $this->loadModel($uniqueDocumentCode);
		if(isset($data->uniqueDocumentCode) && !is_null($data->uniqueDocumentCode)) {
			//if there is no Unit.
			for($i=0;$i<count($data->signers);$i++)
				$test .=Units::item('Signers',$data->signers[$i],$data->OrganizationIdRef ) ."<br/>";
			$data->signers  = $test;
			$data->OrganizationIdRefUnits=	Units::item('Units',$data->OrganizationIdRefUnits,$data->OrganizationIdRef);	
			$ContractItem = new Contractitem();
			
			$ContractData = $ContractItem->findByPk($uniqueDocumentCode,'rfpitem');
			// $contractitem->findByPk($noticeADA,'rfpitem');
			$data->awardProcedure = Lookup::item('award_procedure', $data->awardProcedure);				
			$this->render('viewrfp',array(
				'model'=>$data,
				'ContractData'=>$ContractData,
			));
		}else 				
				$this->render('error',array('code'=>Yii::t('rfp','noview'),'message'=>Yii::t('rfp','noviewmessage')));
	}
	/**
	 * Downloads the file of a selected rfp.
	 * @param integer $id the ID of the model to be displayed
	 */
	public function actionRFPDownload(){
			
			$docURI =$_GET['uniqueDocumentCode'];	
			
			$file = "rfp-".$docURI.".pdf";
			//Connect to ApiConnector
			//sets the config table for the http request. 
			$config['apirequest'] ='rfpdownload';
			$config['documentid'] =$docURI;
			
			//send a curl request for the document 
			$api = new Apiconnector($config);
			$output = $api->makeRequest();
			$download = new filedownload($output,"files",$file);
		}

	
	/**
	 * Create a new contract by Using procurement Ids.
	 * It is a two step form. 
	 * At first step the user selects the ids of procurements.
	 * @param integer $step the number of Step
	 */
	public function actioncreateRfp($step='')
	{
	
		if(!Yii::app()->user->isGuest){
			$step = Yii::app()->request->getParam('step');
			$proc = Yii::app()->request->getParam('proc');
			$awards=1;
			//Check if there are Procurements
			$Item = new Procurement();
			$numOfProcurements = $Item->GetNumberOfProcurement();
			$model = new Rfp();
			$contractitem=new Contractitem();
			$contractitemmanager=new ContractItemManager();
			$model->scenario='createrfp';
			//$this->performAjaxValidation($model);	
			
			//step number
			$step = $step ? $step : 1;
			
			if($step==1){
				if(isset($_POST['Rfp']['procurements']))
				$procurements = $_POST['Rfp']['procurements'];
		
			}else if($step=='2' && $_POST['Rfp']['procurements']=='' && $model->procurements==''){
				$step=1;
				$model->addError('procurements',Yii::t('contract','procurementIds'));
				
			}else if ($step=='2'){
				
				
				$procurements = $_POST['Rfp']['procurements'];
				$model->attributes=$_POST['Rfp'];
				
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
					$test[$i]= $ProcIds[$i];
					$data = $Item->findByPk($ProcIds[$i]);
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
				if(!isset($contractModel->ContractItemId) ) {
					$step=1;
					$model->addError('procurements',Yii::t('contract','procurementIds'));
					for($i=0;$i<count($contractModel);$i++)
						$model->addError('procurements',Yii::t('contract','procdesc')."&nbsp;<u>
							<strong>". $contractModel[$i]['documentUrl']."</strong></u>&nbsp;".Yii::t('contract','procid'.$contractModel[$i]['error']));
				}else{					
					for($i=0;$i<count($contractModel['quantity']);$i++){
						//check if procurements are approved
						if($contractModel->approvesRequest[$i]==''){						
							$step=1;
							$model->addError('procurements',Yii::t('contract','procdesc')."&nbsp;<u>
									<strong>". $contractModel->ContractItemId[$i]."</strong></u>&nbsp;
									".Yii::t('contract','approvesNo'));
						}
						
					}
					//ADA is Set 
					if($_POST['Rfp']['RelatedADA']!=''){
						$model->getADAValues($_POST['Rfp']['RelatedADA']);
						$model->RelatedADA = $_POST['Rfp']['RelatedADA'];
					}
					if($step!=1){
						
						$contractitemmanager=ContractitemManager::load($contractModel);
												
						$procurements = $model->procurements;
						$awards = $contractitem->getAwardProcedure($ProcIds,'procurement');
						$model->awardProcedure=$awards;
						$model->RelatedADA = $_POST['Rfp']['RelatedADA'];
					}
					
				}
				
			}else if($step==3){
				if(isset($_POST['Rfp']) && isset($_POST['Rfp']['title'])){
				
					$this->performAjaxValidation($model);	
					$model->attributes=$_POST['Rfp'];
					if(isset($_POST['Rfp']['RelatedADA']) && $_POST['Rfp']['RelatedADA']!='')
					$model->RelatedADA=$_POST['Rfp']['RelatedADA'];
					if(isset($_POST['Contractitem']))
					$contractitemmanager->manage($_POST['Contractitem']);
					$valid = $model->validate();
					
					if($valid){
						$data = $model->saveToApi();
						if(is_array($data) && !isset($data[0]['error'])){
							$this->successRfp('rfpSuccess', array('model'=>$model,'documentUrl'=>$data[0]['id'],'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'create'));
						}else {
							$errors = new ApiErrorHandling('rfp');
							for($i=0;$i<count($data[0]['error']);$i++){
								$model->addError($errors->NameError($data[0]['error'][$i]),$errors->DescriptionError($data[0]['error'][$i]));	
							}
						}
					}				
				}
			}//end step 3
			
			$this->render('create',array(
				'model'=>$model,
 			    'contractitemmanager'=>$contractitemmanager,			
				'step'=>$step,
				'Mainaction'=>'createrfp',
				'numOfProcurements' =>$numOfProcurements,
			    'modelname'=>'rfp',
			
			
			));
		}else //no permissions to do the action  			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
	}
	/**
	 * Update a selected RFP.
	 */
	public function actionUpdateRfp()
	{
		if(!Yii::app()->user->isGuest){
	    $procs='';
		$id = Yii::app()->request->getParam('uniqueDocumentCode');	
		$model=$this->loadModel($id);
		$model->scenario="update";
		$model->setScenario("update");
		$procurements = isset($model->procurements)?$model->procurements:'';

		if( (!$model->actionPermitted))
			$this->render('error',array('code'=>Yii::t('contract','noAction'),'message'=>Yii::t('contract','noActionmessage')));
		//check if the procurement has been cancelled
		else if ($model->cancelled=='true')
			$this->render('notice',array('code'=>Yii::t('rfp','cancelled'),'message'=>Yii::t('rfp','cancelledmessage')));
			
		else if(!is_null($model->uniqueDocumentCode) && $model->cancelled=='false') {
			
			$contractItem = new Contractitem();
			if($model->procurements!=''){
				//$contractModel = $contractItem->findByMultiplePk($procurements,'procurement');
				$contractModel = $contractItem->findByPk($id,'rfpitem');
				
				$contractitemmanager=ContractItemManager::load($contractModel);
			
			for($i=0;$i<count($procurements);$i++)
				$procs .=$procurements[$i].";";
			}else 
				$contractitemmanager='';
			$model->procurements = $procs; 
			// Uncomment the following line if AJAX validation is needed
			$this->performAjaxValidation($model);
			
			if(isset($_POST['Rfp']) && isset($_POST['Rfp']['title']))
			{
				$model->attributes=$_POST['Rfp'];
				$model->procurements = $_POST['Rfp']['procurements'];
				if(isset($_POST['Contractitem'])){
					$contractitemmanager->manage($_POST['Contractitem']);
				}else
					$model->addError('form',Yii::t('form','noContractItem'));
				$valid = $model->validate();
				if($valid){
					$data = $model->saveToApi('update');
					if(is_array($data) && !isset($data[0]['error'])){
						$this->successRfp('rfpSuccess', array('model'=>$model,'documentUrl'=>$data[0]['id'],'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'update'));
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
				'type'=>'rfp',
			    'modelname'=>'rfp',
			));
		}else
			$this->render('error',array('code'=>Yii::t('contract','noview'),'message'=>Yii::t('contract','noviewmessage')));
			
		}else 
	Yii::app()->request->redirect(Yii::app()->user->returnUrl);	
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
	public function actionDeleteRfp($uniqueDocumentCode)
	{
		if(!Yii::app()->user->isGuest){
			$model=new Rfp();
			$model->scenario='deleterfp';
			$model->findByPk($uniqueDocumentCode);
			
			if ($model->cancelled=='true')
				$this->render('notice',array('code'=>Yii::t('rfp','cancelled'),'message'=>Yii::t('rfp','cancelledmessage')));
		
			else if($model->title){
			
				 if(isset($_POST['Rfp'])){
				$model->attributes=$_POST['Rfp'];
				
				if($_POST['Rfp']['Deldescription']==''){
					$valid = $model->validate();
				}else{
					$config['username']=Yii::app()->user->UserName;
					$config['password']=Yii::app()->user->password;
					$config['apirequest'] ='rfpDelete';
					$config['documentid'] =$uniqueDocumentCode;
					$config['reason'] = $_POST['Rfp']['Deldescription'];
					$apiDelete = new Apiconnector($config);
					$delete = $apiDelete->delete($uniqueDocumentCode,'rfp');

					if($delete==1){
						$this->success('rfpSuccess', array('model'=>$model,'documentUrl'=>$uniqueDocumentCode,
							   'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,
							   'deldescription'=>$_POST['Rfp']['Deldescription'],'action'=>'delete'));
					}else
						$this->errorProc('errorForm',$delete,Yii::t('form','delete'.$delete),'contract');
				
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
	public function actionCancelRfp($uniqueDocumentCode)
	{
		if(!Yii::app()->user->isGuest){
			$model=new Rfp();
			$model->scenario='cancelrfp';
			$model->findByPk($uniqueDocumentCode);
			
			if ($model->cancelled=='true')
				$this->render('notice',array('code'=>Yii::t('procurement','cancelled'),'message'=>Yii::t('procurement','cancelledmessage')));
			else if(isset($_POST['Rfp'])){
				$model->attributes=$_POST['Rfp'];
				
				if($_POST['Rfp']['Deldescription']==''){
					$valid = $model->validate();
				}else{
					$config['username']=Yii::app()->user->UserName;
					$config['password']=Yii::app()->user->password;
					$config['apirequest'] ='rfpcancel';
					$config['documentid'] =$uniqueDocumentCode;
					$ADACancel = $_POST['Rfp']['ADACancel'];
					$delCancel = $_POST['Rfp']['Deldescription'] ."---".$ADACancel."---";
					$config['reason'] = $delCancel;
					$config['cancellationType'] = $_POST['Rfp']['cancellationType'];
					$apiDelete = new Apiconnector($config);
					$cancel = $apiDelete->cancel($uniqueDocumentCode,'rfp');

					if($cancel==1){
						$this->success('rfpSuccess', array('model'=>$model,'documentUrl'=>$uniqueDocumentCode,
							   'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,
							   'deldescription'=>$_POST['Rfp']['Deldescription'],'action'=>'cancel'));
					}else
						$this->errorProc('errorForm',$cancel,Yii::t('form','cancel'.$cancel));
									
				}//there is a reason
			}//post is send
			$this->render('_cancel', array('model'=>$model));
			
		}else 			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
	}		
	
	/**
	 * Action Search 
	 */
	function actionSearchrfp(){
		$model = new Rfp();
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
			if(isset($_POST['Rfp']['title'])){
		    	$title = $_POST['Rfp']['title'];
		    }else $title='';
		    $model->title=$title;      
		    $session = Yii::app()->getComponent('session');
		    $session->add('title',$title);

		    //foreis - ypoforeis 
		    if (Yii::app()->user->isGuest){
			    if(isset($_POST['Rfp']['foreis'])){
			    	$foreis = $_POST['Rfp']['foreis'];
			    }else $foreis='';
			    
			     if(isset($_POST['Rfp']['ypoforeis'])){
			    	$ypoforeis = $_POST['Rfp']['ypoforeis'];
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
		    if(isset($_POST['Rfp']['cpvsid']) && $_POST['Rfp']['cpvsid']!=''){
				$cpv=$_POST['Rfp']['cpvsid'];
					
			}else $cpv='';
			$model->cpvsid=$cpv;
			$session = Yii::app()->getComponent('session');
		    $session->add('cpv',$cpv);
			
		    if(isset($_POST['Rfp']['documentUrl'])){
		    	$ada = $_POST['Rfp']['documentUrl'];
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
				$cost = $cost + $data[$i]['totalCostBeforeVAT'];
			}
		}
			$fm = new CNumberFormatter(Yii::app()->getLocale());
			$cost =$fm->formatCurrency($cost,'EUR');
		$this->render('search',array(	
				'dataProvider'=>$dataProvider,
				'cost'=>$cost,
				'model'=>$model
			));
	
	}
	
	/**
	 * 
	 * Loads the data of the element with the selected id 
	 * @param the document id $id
	 */
	public function loadModel($id){

		$model=new Rfp();
		$model=$model->findByPk($id);

		if($model===null)
			throw new CHttpException(404,'The requested page does not exist.');
		return $model;
	}
	

	
	
	/**
	 * Fetches the list of rfps in a model
	 * It connects to the Apiconnector and fetches the xml (component Apiconnector)
	 * The xml is then parsed (xpath) via the rfpapi data and are stored 
	 * in a model (component RFPapidata.php)
	 * @return a model with the short rfp list data 
	 */
	public function getRfpList(){
		
		//Connect to ApiConnector
		//sets the config table for the http request. 
		$config['apirequest'] ='rfpshort';
		
		$api = new Apiconnector($config);
		$output = $api->makeRequest();
		if($output==500 || $output=='' ||  is_null($output)) return -1;
		
		$Results = new RFPapidata();
		$model = new Rfp();
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
		$OrgNewRef = $_POST['Rfp']['OrganizationIdRefUnits'];
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
	
	public function actionCheck(){
		
		if (Yii::app()->request->isAjaxRequest)
        {
		if(isset($_POST['RelatedADA'])) $RelatedADA = $_POST['RelatedADA'];
		//if(isset($_POST['ADA'])) $RelatedADA = $_POST['ADA'];
		
        $ADAData = new Decision();
		$ADAResults = $ADAData->findByPK($RelatedADA);
		$dateSigned=$protocolNumberCode=$title=$OrganizationIdRefUnits=$signers=$RelatedDocument=$documentCSS=$documentURL=$ada='';
		$RelatedDocumentStatus = 0;
		
		if($ADAResults!=-1){
			$dateSigned = $ADAResults[0]['submissionTimestamp'];
			$protocolNumberCode = $ADAResults[0]['protocolNumber'];
			$title = $ADAResults[0]['title'];
			$OrganizationIdRefUnits = $ADAResults[0]['organizationUnitId'];
			$signers = $ADAResults[0]['signerId'];
			
			$dataStatus ="<img src='images/tick.png' width='24px' height='24px' />"; 
			
		}else
			$dataStatus ="<img src='images/delete.png' width='24px' height='24px' />"; 
		
		 echo CJSON::encode(array(
                    'error'=>'false',
                   	'status'=>$dataStatus, 
                    'dateSigned'=>$dateSigned,
		 			'protocolNumberCode'=>$protocolNumberCode,
		 			'title'=>$title,
		 			'OrgIdRef'=>$OrganizationIdRefUnits,
		 			'signersname'=>$signers,
		 			'ada'=>$RelatedADA,
		 ));
                                        // exit;               
           Yii::app()->end();
        
           }
	
	}
	
	
}
?>