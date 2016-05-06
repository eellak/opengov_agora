<?php
/**
 * 
 * This is the Contoller of Procurement. 
 * It extends AgoraController so as to have our functions mainly 
 * for Messages. 
 * The default functions of Procurement Controller is
 * a) to perform access controls.
 * b) to specify the access controlo rules. 
 * The main functions of Payments Controller is
 * a) View  list of Payments
 * b) Create Payments 
 * c) Update Payments
 * d) Download Payments File
 * @author themiszamani
 *
 */
class PaymentsController extends AgoraController
{
	/**
	 * @var string the default layout for the views. Defaults to '//layouts/column2', meaning
	 * using two-column layout. See 'protected/views/layouts/column2.php'.
	 * It is not necessary to add the type of $layout here because it is defined to the AgoraController
	 */
	public $layout='//layouts/column2';

	/**
	 * The necessary filters to perform access control
	 * @return array action filters
	 */
	public function filters()
	{
		return array(
			'accessControl', // perform access control 
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
			array('allow',  // allow all users to perform 'index' and 'view' actions
				'actions'=>array('indexpayments','viewpayment','downloadpayment','success','dynamicsigners','dynamicsecname',
								'searchpayments','cpvcodes','Dynamicforeis','check'
								,'rss','rss2'),
				'users'=>array('*'),
			),
			array('allow', // allow authenticated user to perform 'create' and 'update' actions
				'actions'=>array('indexpayments','viewpayment','createpayment','createemptypayment','createpaymentbyproc',
								'updatepayment','createpaymentbycontract',
								'downloadpayment','deletePayment','success','cancelpayment'),
				'roles'=>array('admin','authenticated'),
			),
			array('allow', // allow admin user to perform 'admin' and 'delete' actions
				'actions'=>array('indexpayments','viewpayment','createpayment','createemptypayment','createpaymentbycontract',
								'createpaymentbyproc','updatepayment','downloadpayment','deletePayment','success'),
				'roles'=>array('admin'),
			),
			array('allow', // allow admin user to perform 'admin' and 'delete' actions
				'actions'=>array('indexpayments','viewpayment','updatepayment','downloadpayment','deletePayment','success','cancelpayment'),
				'roles'=>array('superadmin'),
			),
			array('deny',  // deny all users
				'users'=>array('*'),
			),
		);
		
	}

	/**
	 * Lists all models.
	 */
	public function actionIndexpayments($search='')
	{
			$Payment = new Payments();
			$search = Yii::app()->request->getParam('search');
			$data = $Payment->getPaymentList($search);
			
			//$dataProvider=$dataProvider->getData(); //will return a list of arrays.
			$dataProvider = new CArrayDataProvider($data, array(
	    	'keyField' => 'documentUrl'     // Agent primary key here
			))	;
			$this->render('index',array(	
				'dataProvider'=>$dataProvider,	
			));
		
	}
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss1 type. Shows the last 20 appoved procurements.
 	 * The limit is set at params file as rsslimit.
	 */
	public function actionRss(){
		$model = new Payments();
		$error=0;
		$search=$name='';
		$data = $model->getPaymentList($search,'',Yii::app()->params['agora']['rsslimit']);
				
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'payments',
			'version'=>'rss1',
		));	
	}
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss2 type. Shows the last 20 appoved payments.
 	 * The limit is set at params file as rsslimit.
	 */
	public function actionRss2(){
		$model = new Payments();
		$error=0;
		$search=$name='';
		$search=$name='';
		$data = $model->getPaymentList($search,'',Yii::app()->params['agora']['rsslimit']);
		
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'payments',
			'version'=>'rss2',
		));
		
	}
	
function actionSearchpayments(){
		$model = new Payments();
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
			if(isset($_POST['Payments']['title'])){
		    	$title = $_POST['Payments']['title'];
		    }else $title='';
		    $model->title=$title;      
		    $session = Yii::app()->getComponent('session');
		    $session->add('title',$title);

		    //foreis - ypoforeis 
		    if (Yii::app()->user->isGuest){
			    if(isset($_POST['Payments']['foreis'])){
			    	$foreis = $_POST['Payments']['foreis'];
			    }else $foreis='';
			    
			     if(isset($_POST['Payments']['ypoforeis'])){
			    	$ypoforeis = $_POST['Payments']['ypoforeis'];
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
		    if(isset($_POST['Payments']['cpvsid']) && $_POST['Payments']['cpvsid']!=''){
				$cpv=$_POST['Payments']['cpvsid'];
					
			}else $cpv='';
			$model->cpvsid=$cpv;
			$session = Yii::app()->getComponent('session');
		    $session->add('cpv',$cpv);
			
		    if(isset($_POST['Payments']['documentUrl'])){
		    	$ada = $_POST['Payments']['documentUrl'];
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
		}else 
			$cost= 0;
			$fm = new CNumberFormatter(Yii::app()->getLocale());
			$cost =$fm->formatCurrency($cost,'EUR');
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
	public function actionViewPayment($documentUrl)
	{
			$data = $this->loadModel($documentUrl);
			//wrong documentUrl
			if(!is_null($data->documentUrl) && !is_null( $data->title )) {
			$uniqueDocumentCode = $documentUrl;
			$ContractItem = new Contractitempayments();
			$ContractData = $ContractItem->findByPk($documentUrl);
			$data->OrganizationIdRefUnits =Units::item('Units',$data->OrganizationIdRefUnits,$data->OrganizationIdRef);
			$data->signers =Units::item('Signers',$data->signers,$data->OrganizationIdRef);
			$data->diavgeiaPublished = (strcmp($data->diavgeiaPublished,"true"))?Yii::t('form','DiavgeiaPYes'):Yii::t('form','DiavgeiaPNo');
			$data->document = "<a href='index.php?r=payments/downloadpayment&documentUrl=".$data->documentUrl.">Download</a>";
			$this->render('view',array(
				'model'=>$data,
				'ContractData'=>$ContractData,
			));
			
			}else 
			$this->render('error',array('code'=>Yii::t('procurement','noview'),'message'=>Yii::t('procurement','noviewmessage')));
			
		
	}
	
	public function actionCreatePayment($step='')
	{
		if(!Yii::app()->user->isGuest){
			$step = Yii::app()->request->getParam('step');
			$contract = Yii::app()->request->getParam('contract');
			
		if(isset($_POST['Payments']['contract']))
				$contract = $_POST['Payments']['contract'];
			else $contract='';
			$step = $step ? $step : 1;
			$contract = $contract ? $contract : 1;
			$procurements=$numOfProc="";
			//print_r($_POST);
			$Item = new Contract();
			$numOfContracts = $Item->GetNumberOfContracts();
			
			$model=new Payments();
			$model->OrganizationIdRef=Yii::app()->user->RefId;
			
			$model->scenario="create";
			$model->setScenario("create");
			
			$contractItem = new Contractitempayments();
			$contractitemmanager=new ContractItempaymentsManager();
			
			if($step==1){
				if(isset($_POST['Payments']['contract']))
				$contract = $_POST['Payments']['contract'];
			}else if($step=='2' && $_POST['Payments']['contract']=='' && $model->contract==''){
				$step=1;
				$model->addError('contract',Yii::t('form','Πρέπει να προσθέσετε τον Μοναδικό Αριθμό της Σύμβασης για να συνεχίσετε' ));
			}else if($step==2){
			
				$contract = $_POST['Payments']['contract'];
				$model->attributes=$_POST['Payments'];
				
				$contractModel = $contractItem->findByPk($contract,"contractsitem");
				$Item->findByPk($contract);
				
				if($Item->cancelled=='true'){						
							$step=1;
							$model->addError('contract',Yii::t('payments','contractdesc')."&nbsp;<u>
									<strong>".  $contract."</strong></u>&nbsp;
									".Yii::t('payments','cancelledmessage'));
				}
				if(!isset($contractModel->ContractItemId) ) {
					$step=1;
					$model->addError('contract',Yii::t('form','Πρέπει να προσθέσετε έναν έγκυρο Μοναδικό Αριθμό της Σύμβασης για να συνεχίσετε' ));
				}else{
					$contractitemmanager=ContractItempaymentsManager::load($contractModel);
					$contract = $model->contract;
				}
			}else if($step==3 && isset($_POST['Payments']) && isset($contract) && $contract!=''){
				if(isset($_POST['Payments']) && isset($_POST['Payments']['title']) && isset($contract) && $contract!=''){
					
					
					$model->attributes=$_POST['Payments'];
					$this->performAjaxValidation($model);	
					$model->attributes=$_POST['Payments'];
					if(isset($_POST['Contractitempayments']))
					$contractitemmanager->manage($_POST['Contractitempayments']);
					
					
					if(isset($_POST['Contractitempayments']))
						$contractitemmanager->manage($_POST['Contractitempayments']);
					else
						Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
					
						$valid = $model->validate();
						$valid = $contractitemmanager->validate($model) && $valid;
						if($valid){
							$data = $model->saveToApi();
							
							if(is_array($data) && !isset($data[0]['error'])){
									$this->successPayment('paymentSuccess', array('model'=>$model,
												   'documentUrl'=>$data[0]['id'],'action'=>'create','title'=>$model->title,
											       'protocolNumberCode'=>$model->protocolNumberCode));
									//$this->redirect(array('indexproc'));
								}else if(is_array($data) && isset($data[0]['error'])) {
									
									for($i=0;$i<count($data[0]['error']);$i++){
										$model->addError('form',Yii::t('form',$data[0]['error'][$i]));
									}
								}else 
									$model->addError('form',Yii::t('form','no middleware'));
								
						//$this->success('paymentSuccess', array('model'=>$model,'documentUrl'=>$contract,'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode));
					}
				}else {
					$step=1;
					$model->addError('contract',Yii::t('form','Πρέπει να προσθέσετε έναν έγκυρο Μοναδικό Αριθμό της Σύμβασης για να συνεχίσετε' ));
			}
			}else {
					$step=1;
					$model->addError('contract',Yii::t('form','Πρέπει να προσθέσετε έναν έγκυρο Μοναδικό Αριθμό της Σύμβασης για να συνεχίσετε' ));
			}
				$this->render('create',array(
				'model'=>$model,
 			    'contractitemmanager'=>$contractitemmanager,
				'step'=>$step,
				'contract'=>$contract,
				'numOfContracts'=>$numOfContracts,
				'scenario'=>$model->scenario,
				'action'=>$model->scenario,
				'procurements'=>$procurements,
				'numOfProc'=>$numOfProc,
			));
			
		}else 			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
	}

	public function actionCreatePaymentByContract($documentUrl)
	{

		if(!Yii::app()->user->isGuest){
			$step = Yii::app()->request->getParam('step');
			$contract = $documentUrl;
			$Item = new Contract();
			$numOfContracts = $Item->GetNumberOfContracts();
			
			$model=new Payments();
			$model->OrganizationIdRef=Yii::app()->user->RefId;
			$model->contract = $contract;
			
			$model->scenario="createpaymentbycontract";
			$model->setScenario("createpaymentbycontract");
			
			$contractItem = new Contractitempayments();
			$contractitemmanager=new ContractItempaymentsManager();
			$contractModel = $contractItem->findByPk($contract,"contractsitem");
			if($contractModel=='404') 
				$this->render('error',array('code'=>Yii::t('payments','wrongContractId'),'message'=>Yii::t('payments','wrongContractIdMessage'),'type'=>'paymentsByContract'));
			else{
			$contractitemmanager=ContractItempaymentsManager::load($contractModel);
			if(isset($_POST['Payments']) && isset($_POST['Payments']['title']) && isset($contract) && $contract!=''){
				$_POST['Payments']['contract'] = $contract;
				$model->attributes=$_POST['Payments'];
				$model->contract = $contract;
				$this->performAjaxValidation($model);	
				$model->attributes=$_POST['Payments'];
				if(isset($_POST['Contractitempayments']))
					$contractitemmanager->manage($_POST['Contractitempayments']);
					if(isset($_POST['Contractitempayments']))
						$contractitemmanager->manage($_POST['Contractitempayments']);
					else
						Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
					
						$valid = $model->validate();
						$valid = $contractitemmanager->validate($model) && $valid;
						if($valid){
							$data = $model->saveToApi();
							if(is_array($data) && !isset($data[0]['error'])){
									$this->successPayment('paymentSuccess', array('model'=>$model,
												   'documentUrl'=>$data[0]['id'],'action'=>'create','title'=>$model->title,
											       'protocolNumberCode'=>$model->protocolNumberCode));
								}else if(is_array($data) && isset($data[0]['error'])) {
									
									for($i=0;$i<count($data[0]['error']);$i++){
										$model->addError('form',Yii::t('form',$data[0]['error'][$i]));
									}
								}else 
									$model->addError('form',Yii::t('form','no middleware'));
								
					}
			}
		
				$this->render('create',array(
				'model'=>$model,
 			    'contractitemmanager'=>$contractitemmanager,
				'step'=>3,
				'contract'=>$contract,
				'contractid'=>$contract,
				'numOfContracts'=>$numOfContracts,
				'scenario'=>$model->scenario,
				'action'=>$model->scenario,
				'procurements'=>'',
				'numOfProc'=>'',
			));
			}
			
		}else 			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
			
	}
	
	public function actionCreatePaymentByProc(){
		if(!Yii::app()->user->isGuest){
			$step = Yii::app()->request->getParam('step');
			$contract = Yii::app()->request->getParam('contract');
			
		if(isset($_POST['Payments']['procurements']))
				$procurements = $_POST['Payments']['procurements'];
		else $procurements='';
		$step = $step ? $step : 1;
		$contract = $contract ? $contract : 1;
			
		//get the number for Procurements. Set null to numOfContracts.
		//If there are no procurements prevent the user from continuing.
		$Item = new Procurement();
		$numOfProc = $Item->GetNumberOfProcurement();
		$numOfContracts="";
		
		//create A new Payment model
		$model=new Payments();
		//Get the OrganizationIdRef from session
		$model->OrganizationIdRef=Yii::app()->user->RefId;
		
		//set scenario name	
		$model->scenario="createpaymentbyproc";
		$model->setScenario("createpaymentbyproc");
		
		//construct the new PaymentItems 
		$contractItem = new Contractitempayments();
		$contractitemmanager=new ContractItempaymentsManager();
		if($step==1){
				if(isset($_POST['Payments']['procurements']))
				$procurements = $_POST['Payments']['procurements'];
		}else if($step=='2' && $_POST['Payments']['procurements']=='' && $model->procurements==''){
				$step=1;
				$model->addError('procurements',Yii::t('form','Πρέπει να προσθέσετε τον Μοναδικό Αριθμό του Αιτήματος για να συνεχίσετε' ));
			}else if($step==2){
				$procurements = $_POST['Payments']['procurements'];
				$model->attributes=$_POST['Payments'];
				$model->procurements=$procurements;
				
				$ProcIds = explode(";", $procurements);
				$contractModel = $contractItem->findByMultiplePk($ProcIds,"procurement");
				
				if(!isset($contractModel->ContractItemId)){
					$step=1;
					$model->addError('procurements',Yii::t('form','Πρέπει να προσθέσετε έναν έγκυρο Μοναδικό Αριθμό Αιτήματος για να συνεχίσετε' ));
				}else{
					for($i=0;$i<count($contractModel['quantity']);$i++){
						if($contractModel->approvesRequest[$i]==''){						
							$step=1;
							$model->addError('procurements',Yii::t('contract','procdesc')."&nbsp;<u>
									<strong>". $contractModel->ContractItemId[$i]."</strong></u>&nbsp;
									".Yii::t('contract','approvesNo'));
						}
						$Item->findByPk($contractModel->ContractItemId[$i]);
						if($Item->cancelled=='true'){						
							$step=1;
							$model->addError('procurements',Yii::t('contract','procdesc')."&nbsp;<u>
									<strong>". $contractModel->ContractItemId[$i]."</strong></u>&nbsp;
									".Yii::t('procurement','cancelledmessage'));
					}
					}
					if($step!=1){
						$contractitemmanager=ContractItempaymentsManager::load($contractModel);
						$procurements = $model->procurements;
					}
				}
			}else if($step==3 && isset($_POST['Payments']) && isset($procurements) && $procurements!=''){
				if(isset($_POST['Payments']) && isset($_POST['Payments']['title'])){
					$_POST['Payments']['procurements']=$procurements;
					$model->attributes=$_POST['Payments'];
					$this->performAjaxValidation($model);	
					$model->attributes=$_POST['Payments'];
					$model->procurements=$_POST['Payments']['procurements'];
					if(isset($_POST['Contractitempayments']))
					$contractitemmanager->manage($_POST['Contractitempayments']);
					if(isset($_POST['Contractitempayments']))
						$contractitemmanager->manage($_POST['Contractitempayments']);
					else
						Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
					
		
						$valid = $model->validate();
						$valid = $contractitemmanager->validate($model) && $valid;
						
						if($valid){
							$data = $model->saveToApi();
							
							if(is_array($data) && !isset($data[0]['error'])){
									$this->successPayment('paymentSuccess', array('model'=>$model,
												   'documentUrl'=>$data[0]['id'],'action'=>'create','title'=>$model->title,
											       'protocolNumberCode'=>$model->protocolNumberCode));
									//$this->redirect(array('indexproc'));
								}else if(is_array($data) && isset($data[0]['error'])) {
									
									for($i=0;$i<count($data[0]['error']);$i++){
										$model->addError('form',Yii::t('form',$data[0]['error'][$i]));
									}
								}else 
									$model->addError('form',Yii::t('form','no middleware'));
								
						//$this->success('paymentSuccess', array('model'=>$model,'documentUrl'=>$contract,'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode));
					}
				}else {
					$step=1;
					$model->addError('procurements',Yii::t('form','Πρέπει να προσθέσετε έναν έγκυρο Μοναδικό Αριθμό της Σύμβασης για να συνεχίσετε' ));
			}
			}else {
					$step=1;
					$model->addError('procurements',Yii::t('form','Πρέπει να προσθέσετε έναν έγκυρο Μοναδικό Αριθμό της Σύμβασης για να συνεχίσετε' ));
			}
				$this->render('create',array(
				'model'=>$model,
 			    'contractitemmanager'=>$contractitemmanager,
				'step'=>$step,
				'contract'=>$contract,
				'procurements'=>$procurements,
				'numOfContracts'=>$numOfContracts,
				'scenario'=>$model->scenario,
				'action'=>$model->scenario,
				'numOfProc'=>$numOfProc,
				
			));
			
		}else 			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
	
		
	}
	public function actionCreateEmptyPayment()
	{
		
		if(!Yii::app()->user->isGuest){
			
			$model=new Payments();
			$model->OrganizationIdRef=Yii::app()->user->RefId;
			
			$model->scenario="createemptypayment";
			$model->setScenario("createemptypayment");
			
			
			$contractItem = new Contractitempayments();
			$contractitemmanager=new ContractItempaymentsManager();
			
			if(isset($_POST['Payments']) && isset($_POST['Payments']['title'])){
				$model->attributes=$_POST['Payments'];
				$this->performAjaxValidation($model);	
				$model->attributes=$_POST['Payments'];
				if(isset($_POST['Contractitempayments']))
					$contractitemmanager->manage($_POST['Contractitempayments']);
					if(isset($_POST['Contractitempayments']))
						$contractitemmanager->manage($_POST['Contractitempayments']);
					else
						Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
					
						$valid = $model->validate();
						$valid = $contractitemmanager->validate($model) && $valid;
						if($valid){
							$data = $model->saveToApi();
					
							if(is_array($data) && !isset($data[0]['error'])){
									$this->successPayment('paymentSuccess', array('model'=>$model,
												   'documentUrl'=>$data[0]['id'],'action'=>'create','title'=>$model->title,
											       'protocolNumberCode'=>$model->protocolNumberCode));
								}else if(is_array($data) && isset($data[0]['error'])) {
									
									for($i=0;$i<count($data[0]['error']);$i++){
										$model->addError('form',Yii::t('form',$data[0]['error'][$i]));
									}
								}else 
									$model->addError('form',Yii::t('form','no middleware'));
								
					}
			}
		
				$this->render('createEmpty',array(
				'model'=>$model,
 			    'contractitemmanager'=>$contractitemmanager,
			));
		
			
		}else 			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
			
	}
/**
	 * Updates a particular model.
	 * If update is successful, the browser will be redirected to the 'view' page.
	 * @param integer $id the ID of the model to be updated
	 */
	public function actionUpdatePayment($documentUrl)
	{
		//http://e-mats.org/2008/07/fatal-error-exception-thrown-without-a-stack-frame-in-unknown-on-line-0/
		$documentUrl = Yii::app()->request->getParam('documentUrl');	
		$model=new Payments();
		$model=$this->loadModel($documentUrl);
		
		$scenario=$action=$model->scenario="update";
		$model->setScenario("update");
		$contractItem = new Contractitempayments();
		$contractitemmanager=new ContractItempaymentsManager();
		
		$numOfProc="";
		if( (!$model->actionPermitted))
			$this->render('error',array('code'=>Yii::t('procurement','noAction'),'message'=>Yii::t('procurement','noActionmessage')));
		else if($model->cancelled=='true'){
			$this->render('notice',array('code'=>Yii::t('payments','cancelled'),'message'=>Yii::t('payments','cancelledmessage')));
		}else {
			//wrong $documentUrl
			if((!is_null($model->documentUrl) && !is_null( $model->title )) ) {
			
			$procurements = isset($model->procurements)?$model->procurements:'';
			$contract = isset($model->contract)?$model->contract:'';
				
			//load contractItemPayments for this payment
			$contractModel = $contractItem->findByPk($documentUrl);
			$contractitemmanager=ContractitempaymentsManager::load($contractModel);
			// Uncomment the following line if AJAX validation is needed
			$this->performAjaxValidation($model);
						
			if(isset($_POST['Payments'])){
				$model->attributes=$_POST['Payments'];
				$model->contract = $contract;
				$model->procurement = $procurements;
				
				$valid = $model->validate();

				if(isset($_POST['Contractitempayments'])){
					$contractitemmanager->manage($_POST['Contractitempayments']);
				}else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
					$valid = $contractitemmanager->validate() && $valid;
				if($valid){
						$saved = $model->saveToApi('update');
						if(is_array($saved) && !isset($saved[0]['error'])){
							$this->successPayment('paymentSuccess', array('model'=>$model,
														'documentUrl'=>$documentUrl,'title'=>$model->title,
										       'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'update'));
					
						}else{
								$errors = new ApiErrorHandling('payment');
							for($i=0;$i<count($saved[0]['error']);$i++){
								$model->addError($errors->NameError($saved[0]['error'][$i]),$errors->DescriptionError($saved[0]['error'][$i]));	
							}
						}
				}
			}
				
				$this->render('update',array(
					'model'=>$model,
			    	'contractitemmanager'=>$contractitemmanager,
					'scenario'=>$model->scenario,
					'action'=>$model->scenario,
					'procurements'=>$procurements,
					'numOfProc'=>$numOfProc,
				));
			}else
				$this->render('error',array('code'=>Yii::t('procurement','noview'),'message'=>Yii::t('procurement','noviewmessage')));
		}//end actionPermitted
		
	}
	
	
		public function actionDownloadPayment(){
			
			$docURI =$_GET['documentUrl'];	
			
			$file = "payments-".$docURI.".pdf";
			
			//Connect to ApiConnector
			//sets the config table for the http request. 
			$config['apirequest'] ='paymentsDownload';
			$config['documentid'] =$docURI;
			
			//send a curl request for the document 
			$api = new Apiconnector($config);
			$output = $api->makeRequest();
			
			$download = new filedownload($output,"files",$file);
		}
		
	/**
	 * Deletes a particular model.
	 * If deletion is successful, the browser will be redirected to the 'index' page.
	 * While deleting the following cases may appear
	 * a) Successful deletion --> $delete=1
	 * b) Unauthorized deletion --> $delete=403
	 * c) Trying to delete a procurement that doesnt exist --> $delete=404
	 * @param integer $id the ID of the model to be deleted
	 */
	public function actionDeletePayment($uniqueDocumentCode)
	{
		$model=new Payments();
		$model->scenario='deletepayment';
		$model->findByPk($uniqueDocumentCode);

		if( (!$model->actionPermitted))
			$this->render('error',array('code'=>Yii::t('procurement','noAction'),'message'=>Yii::t('procurement','noActionmessage')));
		else if($model->cancelled=='true')
			$this->render('notice',array('code'=>Yii::t('payments','cancelled'),'message'=>Yii::t('payments','cancelledmessage')));
		else {
		if(isset($_POST['Payments'])){
			$model->attributes=$_POST['Payments'];
			$Procurement_Deldescription = Yii::app()->request->getParam('Procurement_Deldescription');
		
			if($_POST['Payments']['Deldescription']==''){
				$valid = $model->validate();
			}else{
				
				$config['username']=Yii::app()->user->UserName;
				$config['password']=Yii::app()->user->password;
				$config['documentid'] =$uniqueDocumentCode;
				$config['reason'] = $_POST['Payments']['Deldescription'];
				$apiDelete = new Apiconnector($config);
				$delete = $apiDelete->delete($uniqueDocumentCode,'payment');
				if($delete==1){
					$this->successPayment('paymentSuccess', array('model'=>$model,'documentUrl'=>$uniqueDocumentCode,
						   'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,
						   'deldescription'=>$_POST['Payments']['Deldescription'],'action'=>'delete'));
				}else
					$this->errorProc('errorForm',$delete,Yii::t('form','delete'.$delete));
			}
		}
				 $this->render('_delete', array('model'=>$model));
		}
	   
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
	public function actionCancelPayment($documentUrl)
	{
		if(!Yii::app()->user->isGuest){
			$model=new Payments();
			$model->scenario='cancelpayment';
			$model->findByPk($documentUrl);
			
			if ($model->cancelled=='true')
				$this->render('notice',array('code'=>Yii::t('procurement','cancelled'),'message'=>Yii::t('procurement','cancelledmessage')));
			else if(isset($_POST['Payments'])){
				$model->attributes=$_POST['Payments'];
				
				if($_POST['Payments']['Deldescription']==''){
					$valid = $model->validate();
				}else{
					$config['username']=Yii::app()->user->UserName;
					$config['password']=Yii::app()->user->password;
					$config['apirequest'] ='paymentcancel';
					$config['documentid'] =$documentUrl;
					$ADACancel = $_POST['Payments']['ADACancel'];
					$delCancel = $_POST['Payments']['Deldescription'] ."---".$ADACancel."---";
					$config['reason'] = $delCancel;
					$config['cancellationType'] = $_POST['Payments']['cancellationType'];
					$apiDelete = new Apiconnector($config);
					$cancel = $apiDelete->cancel($documentUrl,'payment');

					if($cancel==1){
						$this->success('paymentSuccess', array('model'=>$model,'documentUrl'=>$documentUrl,
							   'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,
							   'deldescription'=>$_POST['Payments']['Deldescription'],'action'=>'cancel'));
					}else
						$this->errorProc('errorForm',$cancel,Yii::t('form','cancel'.$cancel));
									
				}//there is a reason
			}//post is send
			$this->render('_cancel', array('model'=>$model));
			
		}else 			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
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
	 * Returns the data model based on the primary key given in the GET variable.
	 * If the data model is not found, an HTTP exception will be raised.
	 * @param integer the ID of the model to be loaded
	 */
	public function loadModel($id)
	{
		$model=new Payments();
		$model=$model->findByPk($id);
		
		
		if($model===null)
			throw new CHttpException(404,'The requested page does not exist.');
		return $model;
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
	 * Dependent drop down menu Units - Signers. 
	 * This function is used to generate the second level 
	 * drop down menu of Signers. The user selects a unit 
	 * from the dropdown menu and an ajax request is made 
	 * to create the second level menu. It is used in _form.php.
	 */
	public function actionDynamicsigners(){
		$OrgNewRef = $_POST['Payments']['OrganizationIdRefUnits'];
		$data= Units::itemsSigners('Signers',$OrgNewRef);
		
		foreach($data as $value=>$name)
        	echo CHtml::tag('option',array('value'=>$value),CHtml::encode($name),true);
            
	}
	
public function actionDynamicsecname(){
		
		//if(isset($_POST['Payments_OrganizationIdRefUnits']))
//		/$OrgNewRef =  $_POST['Payments_OrganizationIdRefUnits'];
		
		$AFM= $_POST['Contractitempayments'][$_GET['number']]['Secafm'];
		$contractid = $_POST['Payments']['contract'];
		$number = $_GET['number'];
		
		$contractItem = new Signers();
		$contractModel = $contractItem->findByPk($contractid);
		//print_r($contractModel);
		
		$secname='';
		for($i=0;$i<count($contractModel);$i++){
			if($AFM==$contractModel['Secafm'][$i]){
				break;	
			} 
		}
		$name = "Contractitempayments[".$number."][Secname]";
		$id = "Contractitempayments[".$number."][Secname]";
		$name1 = "Contractitempayments[".$number."][Seccountry]";
		$id1 = "Contractitempayments[".$number."][Seccountry]";
		
		echo"<div class=\"row\">";
		echo CHtml::activeLabel($contractItem,'Secname');
        echo CHtml::tag('input', array( 'type'=>'text' ,'name'=>$name, 'id'=>$id, 'value' => $contractModel['Secname'][$i],'class'=>'Secname'));
		echo"</div><div class=\"row\">";
        echo CHtml::activeLabel($contractItem,'Seccountry');
        echo CHtml::dropDownList($name1,$contractModel['Seccountry'][$i],Lookup::items('country',$contractModel['Seccountry'][$i]),array('class'=>'country1'));
        echo"</div>";
	
		
	}
	
	/**
	 * Dependent drop down menu foreis - ypoforeis. 
	 * This function is used to generate the second level 
	 * drop down menu of ypoforeis. The user selects a unit 
	 * from the dropdown menu and an ajax request is made 
	 * to create the second level menu. It is used in _form.php.
	 */
	public function actionDynamicforeis(){
		$foreis = $_POST['Payments']['foreis'];
		$data= ODE::items('withid',$foreis);
		
		foreach($data as $value=>$name)
			echo CHtml::tag('option',array('value'=>$value),CHtml::encode($name),true);
	}
	
public function actionCheck(){
		$RelatedADA='';
		if (Yii::app()->request->isAjaxRequest)
        {
		
		$RelatedADA = $_POST['Related_ADA'];
        $ADAData = new Decision();
		$ADAResults = $ADAData->findByPK($RelatedADA);		
		$dateSigned=$protocolNumberCode=$title=$OrganizationIdRefUnits=$signers=$RelatedDocument=$documentCSS=$documentURL='';
		$RelatedDocumentStatus = 0;
		
		if($ADAResults!=-1){
			$dateSigned = $ADAResults[0]['submissionTimestamp'];
			$protocolNumberCode = $ADAResults[0]['protocolNumber'];
			$title = $ADAResults[0]['title'];
			$OrganizationIdRefUnits = $ADAResults[0]['organizationUnitId'];
			$signers = $ADAResults[0]['signerId'];
			$dataStatus ="<img src='images/tick.png' width='24px' height='24px' />"; 
			$RelatedDocument = $ADAResults[0]['documentUrl'];
			if($RelatedDocument!=''){
				$RelatedDocumentStatus = 1;
				$documentCSS="'display','none'";
				$documentURL = "<center>\n".
				"<div class=\"download\">\n".
				 CHtml::link(CHtml::encode(Yii::t('form','downloadFile')),$RelatedDocument).
				"</div>\n".
				"</center>\n";
			}
			
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
		 			'RelatedDocument'=>$RelatedDocument,
		 			'documentCSS'=>$documentCSS,
		 			'documentURL'=>$documentURL,
		 ));
                                        // exit;               
           Yii::app()->end();
        
        
           }
	
	}
	
	
}