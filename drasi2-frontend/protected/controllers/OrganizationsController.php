<?php
/**
 * 
 * This is the Contoller of Organizations.
 * It extends AgoraController so as to have our functions mainly 
 * for Messages. 
 * The default functions of Organizations Controller is
 * a) to perform access controls.
 * b) to specify the access controlo rules. 
 * The main functions of Organizations Controller is
 * a) View  list of organizations
 * b) View Procurements
 * c) View RFPS
 * d) View Contracts
 * e) View Payments
 * @author themiszamani
 *
 */
class OrganizationsController extends Controller
{
	/**
	 * @var string the default layout for the views. Defaults to '//layouts/column2', meaning
	 * using two-column layout. See 'protected/views/layouts/column2.php'.
	 * It is not necessary to add the type of $layout here because it is defined to the AgoraController
	 */
	public $layout='//layouts/column2PerOrg';
	public $homeUrl;

	function  init(){
		$this->layout = '//layouts/column2PerOrg';
		 
		 Yii::app()->setComponents(array(
            'errorHandler'=>array(
            'errorAction'=>'organizations/error',
        ),));
	}
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
				'actions'=>array('indexall','indexdetails','startprocperorg','indexprocperorg','viewproc','downloadproc','error',
								'indexrfpperorg','viewrfp',
								'indexcontractsperorg','viewcontract',
								'indexpaymentsperorg','viewpayment','contactus',
								'foreis','foreisEPOP','RssProc','Rss2Proc','RssRfp','Rss2Rfp',
								'RssContract','Rss2Contract','RssPayments','Rss2Payments','rss',
								),
				'users'=>array('*'),
			),
			
			array('deny',  // deny all users
				'users'=>array('*'),
			),
		);
	}
	
	public function actionIndexAll(){
		
		
		$dataProvider=$dataProviderAr=$dataProviderDhmoi=$dataProviderPer=array();
		/*
		 *
		 $Ypourgeia = Organizations::getYpourgeia();
	
		$dataProvider = new CArrayDataProvider($Ypourgeia, array(
		    	'keyField' => 'title'     // Agent primary key here
		));
	
		 $Arxes = Organizations::getArxes();
	
		$dataProviderAr = new CArrayDataProvider($Arxes, array(
    	'keyField' => 'title'     // Agent primary key here
		));
		
		$Per = Organizations::getPeriferies();
		$dataProviderPer = new CArrayDataProvider($Per, array(
		    	'keyField' => 'title'     // Agent primary key here
		));
		$Dhmoi = Organizations::getDhmous();
		$dataProviderDhmoi = new CArrayDataProvider($Dhmoi, array(
		    	'keyField' => 'title',     // Agent primary key here
		));*/
		$this->render('startAll', array(
				'dataProvider'=>$dataProvider,
				'dataProviderAr'=>$dataProviderAr,
				'dataProviderPer'=>$dataProviderPer,
				'dataProviderDhmoi'=>$dataProviderDhmoi,
				)
		);
	}
	public function actionIndexDetails(){
		$this->render('startAllDetails');
	}
	public function actionContactus(){
		$this->render('contactus');
	}
	public function actionForeis(){
		$this->render('foreis');
	}
	public function actionForeisEPOP(){
		$this->render('foreisEPOP');
	}
	public function actionRss(){
		$this->render('rss');
	}
	public function actionStartProcPerOrg($org){
		$org = Yii::app()->request->getParam('org');
		if(isset($org) && $org==''){
			$this->render('error',array('code'=>Yii::t('procurement','noviewOrg'),'message'=>Yii::t('procurement','noviewOrgmessage')));
		}else{
		$search=$name='';
		$limit=1;
		$Organizations = new Organizations();
		$orgId = $Organizations->getOrgId($org);
		$model = new Procurement();
		$data = $model->getProcurementList($search,$name,$orgId,$limit);
		
		$dataProvider = new CArrayDataProvider($data, array(
	    	'keyField' => 'documentUrl',     // Agent primary key here
		));
		
		$FData = new Contract();
		$dataContract = $FData->getContractListTable($search,$orgId,$limit);
		$dataProviderContract = new CArrayDataProvider($dataContract, array(
    	'keyField' => 'uniqueDocumentCode'     // Agent primary key here
		));
		
		$Rfp = new Rfp();
		$dataRfp = $Rfp->getRfpListTable($search,$orgId,$limit);
		//CArrayDataProvider need ID to identification unique column value for ID CGridView
		//So i always have to change the KeyField
		//http://www.yiiframework.com/forum/index.php?/topic/17561-cgridview-and-1-to-1-relationship/
		$dataProviderRfp = new CArrayDataProvider($dataRfp, array(
    	'keyField' => 'uniqueDocumentCode'     // Agent primary key here
		));
		
		$Payment = new Payments();
		$dataPayment = $Payment->getPaymentList($search,$orgId,$limit);
			
		//$dataProvider=$dataProvider->getData(); //will return a list of arrays.
		$dataProviderPayment = new CArrayDataProvider($dataPayment, array(
	    	'keyField' => 'documentUrl'     // Agent primary key here
		));
		
		$this->render('indexStart',array(	
			'dataProvider'=>$dataProvider,
			'dataProviderContract'=>$dataProviderContract,
			'dataProviderRfp'=>$dataProviderRfp,
			'dataProviderPayment'=>$dataProviderPayment,
			'org'=>$org
		));
		}
	}
	
	public function actionIndexprocPerOrg($search='notapproved',$name='type',$org='')
	{
		$result='';
		$model = new Procurement();
		$org = Yii::app()->request->getParam('org');	
		$Organizations = new Organizations();
		$orgId = $Organizations->getOrgId($org);
		
		if($search!='notapproved' && $search!='approved' && $search!='approval') $name='';
		if($search!='')
			$data = $model->getProcurementList($search,$name,$orgId);
		else
			$data = $model->getProcurementList();
			//$this->actionExportx($data);
		$dataProvider = new CArrayDataProvider($data, array(
	    	'keyField' => 'documentUrl',     // Agent primary key here
		));
			
		$this->render('index',array(	
			'dataProvider'=>$dataProvider,
			'org'=>$org
		));
		
	}

	/**
	 * Displays the procurement model.
	 * @param integer $id the ID of the model to be displayed
	 */
	function actionViewProc($documentUrl){
		$procData = new Procurement();
		$procData->findByPk($documentUrl);
			//wrong documentUrl
		$org = Yii::app()->request->getParam('org');
		$Organizations = new Organizations();
		$org = $Organizations->getOrgId($org);	
		if(isset($procData->OrganizationIdRef) && $procData->OrganizationIdRef!=$org) {
				$this->render('error',array('code'=>Yii::t('procurement','noviewOrg'),'message'=>Yii::t('procurement','noviewOrgmessage')));
			
		}else if(!is_null($procData->documentUrl) && !is_null( $procData->title ) ) {		
					$ContractItem = new Contractitem();
					$ContractData = $ContractItem->findByPk($documentUrl,'procurement');
					$procData->OrganizationIdRefUnits=	Units::item('Units',$procData->OrganizationIdRefUnits,$procData->OrganizationIdRef);
					$procData->signers =Units::item('Signers',$procData->signers,$procData->OrganizationIdRef);
					$procData->awardProcedure = Lookup::item('award_procedure', $procData->awardProcedure);				
					$procData->document = "<a href='index.php?r=organizations/downloadproc&documentUrl=".$procData->documentUrl.">Download</a>";
					$this->render('view',array(
						'model'=>$procData,
						'ContractData'=>$ContractData,
					));
			}else 
				$this->render('error',array('code'=>Yii::t('procurement','noview'),'message'=>Yii::t('procurement','noviewmessage')));
	}
	
	
	public function actionIndexContractsPerOrg($search='',$org='')
	{
		
		$search = Yii::app()->request->getParam('search');
		$org = Yii::app()->request->getParam('org');	
		$Organizations = new Organizations();
		$orgId = $Organizations->getOrgId($org);
		
		$FData = new Contract();
		$data = $FData->getContractListTable($search,$orgId);
	
		//CArrayDataProvider need ID to identification unique column value for ID CGridView
		//So i always have to change the KeyField
		//http://www.yiiframework.com/forum/index.php?/topic/17561-cgridview-and-1-to-1-relationship/
		$dataProvider = new CArrayDataProvider($data, array(
    	'keyField' => 'uniqueDocumentCode'     // Agent primary key here
		));
		$this->render('indexcontracts',array(	
			'dataProvider'=>$dataProvider,
			'org'=>$org
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
		$org = Yii::app()->request->getParam('org');
		$Organizations = new Organizations();
		$org = $Organizations->getOrgId($org);
		$contract = new Contract();
		$contract->findByPk($uniqueDocumentCode);
		if(isset($contract->OrganizationIdRef) && $contract->OrganizationIdRef!=$org) {
				$this->render('error',array('code'=>Yii::t('contract','noviewOrg'),'message'=>Yii::t('contract','noviewOrgmessage')));
			
		}else if(isset($contract->uniqueDocumentCode) && !is_null($contract->uniqueDocumentCode)) {

			//if there is no Unit.
			$contract->OrganizationIdRefUnits=	Units::item('Units',$contract->OrganizationIdRefUnits,$contract->OrganizationIdRef);	
			$ContractItem = new Contractitem();
			$ContractData = $ContractItem->findByPk($uniqueDocumentCode,'contractsitem');
			$Signers = new Signers();
			$SignersData = $Signers->findByPk($uniqueDocumentCode,'contractsitem');
			for($i=0;$i<count($contract->signers);$i++)
				$test .=Units::item('Signers',$contract->signers[$i],$contract->OrganizationIdRef ) ."<br/>";
				$contract->signers  = $test;
				$contract->contractType =Lookup::item('contractType',$contract->contractType);
				$contract->commissionCriteria =Lookup::item('commissionCriteria',$contract->commissionCriteria);
				$contract->contractingAuthority = Lookup::item('contracting_authority', $contract->contractingAuthority);
				$contract->awardProcedure = Lookup::item('award_procedure', $contract->awardProcedure);				
				$contract->diavgeiaPublished = (strcmp($contract->diavgeiaPublished,"true"))?Yii::t('form','DiavgeiaPNo'):Yii::t('form','DiavgeiaPYes');
				$contract->document= "<a href='index.php?r=contract/contractdownload&uniqueDocumentCode=".$contract->uniqueDocumentCode.">Download</a>";
			$this->render('viewcontract',array(
				'model'=>$contract,
				'ContractData'=>$ContractData,
				'SignersData' =>$SignersData,
			));
			}else 				
				$this->render('error',array('code'=>Yii::t('contract','noview'),'message'=>Yii::t('contract','noviewmessage')));
			
			
	
	}
	/**
	 * Lists all rfp.
	 */
	public function actionIndexRfpPerOrg($search="",$org='')
	{
		$search = Yii::app()->request->getParam('search');
		$org = Yii::app()->request->getParam('org');	
		$Organizations = new Organizations();
		$org = $Organizations->getOrgId($org);
		$Rfp = new Rfp();
		$data = $Rfp->getRfpListTable($search,$org);
		//CArrayDataProvider need ID to identification unique column value for ID CGridView
		//So i always have to change the KeyField
		//http://www.yiiframework.com/forum/index.php?/topic/17561-cgridview-and-1-to-1-relationship/
		$dataProvider = new CArrayDataProvider($data, array(
    	'keyField' => 'uniqueDocumentCode'     // Agent primary key here
		));
		$this->render('indexrfpperorg',array(	
			'dataProvider'=>$dataProvider,
			'org'=>$org
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
		$org = Yii::app()->request->getParam('org');
		$Organizations = new Organizations();
		$org = $Organizations->getOrgId($org);
			//wrong documentUrl
		$Rfp = new Rfp();
		$data = $Rfp->findByPk($uniqueDocumentCode);
		if(isset($data->OrganizationIdRef) && $data->OrganizationIdRef!=$org) {
			$this->render('error',array('code'=>Yii::t('rfp','noviewOrg'),'message'=>Yii::t('rfp','noviewOrgmessage')));
			
		}else if(isset($data->uniqueDocumentCode) && !is_null($data->uniqueDocumentCode)) {
			//if there is no Unit.
			for($i=0;$i<count($data->signers);$i++)
				$test .=Units::item('Signers',$data->signers[$i],$data->OrganizationIdRef ) ."<br/>";
			$data->signers  = $test;
			$data->OrganizationIdRefUnits=	Units::item('Units',$data->OrganizationIdRefUnits,$data->OrganizationIdRef);	
			$ContractItem = new Contractitem();
			$ContractData = $ContractItem->findByMultiplePk($data->procurements,'procurement');
			$data->awardProcedure = Lookup::item('award_procedure', $data->awardProcedure);				
			$this->render('viewrfp',array(
				'model'=>$data,
				'ContractData'=>$ContractData,
			));
		}else 				
				$this->render('error',array('code'=>Yii::t('rfp','noview'),'message'=>Yii::t('rfp','noviewmessage')));
	}
	
/**
	 * Lists all models.
	 */
	public function actionIndexpaymentsPerOrg($search='',$org='')
	{
			$Payment = new Payments();
			$org = Yii::app()->request->getParam('org');
			$Organizations = new Organizations();
			$org = $Organizations->getOrgId($org);
			
			$search = Yii::app()->request->getParam('search');
			$data = $Payment->getPaymentList($search,$org);
			
			//$dataProvider=$dataProvider->getData(); //will return a list of arrays.
			$dataProvider = new CArrayDataProvider($data, array(
	    	'keyField' => 'documentUrl'     // Agent primary key here
			))	;
			$this->render('indexpaymentsperorg',array(	
				'dataProvider'=>$dataProvider,	
			));
		
	}
	
	
	/**
	 * Displays a particular model.
	 * @param integer $id the ID of the model to be displayed
	 */
	public function actionViewPayment($documentUrl)
	{
			$payment = new Payments();
			$data = $payment->findByPk($documentUrl);
			$org = Yii::app()->request->getParam('org');
			$Organizations = new Organizations();
			$org = $Organizations->getOrgId($org);
			//wrong documentUrl
			if(isset($data->OrganizationIdRef) && $data->OrganizationIdRef!=$org) {
				$this->render('error',array('code'=>Yii::t('payments','noviewOrg'),'message'=>Yii::t('payments','noviewOrgmessage')));
			
			}else if(isset($data->uniqueDocumentCode) && !is_null($data->uniqueDocumentCode)) {
			$uniqueDocumentCode = $documentUrl;
			$ContractItem = new Contractitempayments();
			$ContractData = $ContractItem->findByPk($documentUrl);

			$data->OrganizationIdRefUnits =Units::item('Units',$data->OrganizationIdRefUnits,$data->OrganizationIdRef);
			$data->signers =Units::item('Signers',$data->signers,$data->OrganizationIdRef);
			$data->diavgeiaPublished = (strcmp($data->diavgeiaPublished,"true"))?Yii::t('form','DiavgeiaPYes'):Yii::t('form','DiavgeiaPNo');
			$data->document = "<a href='index.php?r=payments/downloadpayment&documentUrl=".$data->documentUrl.">Download</a>";
			$this->render('viewpayment',array(
				'model'=>$data,
				'ContractData'=>$ContractData,
			));
			
			}else 
			$this->render('error',array('code'=>Yii::t('procurement','noview'),'message'=>Yii::t('procurement','noviewmessage')));
			
		
	}
	/**
	/**
	 * This is the action to handle external exceptions.
	 * Specific for each controller
	 */
		public function actionError()
        {
        if($error=Yii::app()->errorHandler->error)
        {
        	
                if(Yii::app()->request->isAjaxRequest)
                        echo $error['message'];
                else {
                	$this->layout='column2PerOrg';
                    $this->render('error', $error);
                }
                    
        }
            
        }
        
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss1 type. Shows the last 20 appoved procurements.
	 * The limit is set at params file as rsslimit.
	 */
	public function actionRssProc(){
		$result='';
		$model = new Procurement();
		$org = Yii::app()->request->getParam('org');	
		$Organizations = new Organizations();
		$orgId = $Organizations->getOrgId($org);
		$error=0;
		$search=$name='';
		$search='approval';	$name='type';
		$data = $model->getProcurementList($search,$name,$orgId,Yii::app()->params['agora']['rsslimit']);
		
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'procurement',
			'version'=>'rss1',
			'org'=>$org,
		));
				
	}
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss2 type. Shows the last 20 appoved procurements.
	 * The limit is set at params file as rsslimit.
	 */
	public function actionRss2Proc(){
		$result='';
		$model = new Procurement();
		$org = Yii::app()->request->getParam('org');	
		$Organizations = new Organizations();
		$orgId = $Organizations->getOrgId($org);
		$error=0;
		$search=$name='';
		$search='approval';	$name='type';
		$data = $model->getProcurementList($search,$name,$orgId,Yii::app()->params['agora']['rsslimit']);
		
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'procurement',
			'version'=>'rss2',
			'org'=>$org,
		));
				
	}
/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss1 type. Shows the last 20 appoved Rfps.
	 * The limit is set at params file as rsslimit.
	 */
	public function actionRssRfp(){
		$result='';
		$org = Yii::app()->request->getParam('org');	
		$Organizations = new Organizations();
		$orgId = $Organizations->getOrgId($org);
		$error=0;
		$search='';
		$Rfp = new Rfp();
		$data = $Rfp->getRfpListTable($search,$orgId,Yii::app()->params['agora']['rsslimit']);
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'rfp',
			'version'=>'rss1',
			'org'=>$org,
		));
				
	}
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss2 type. Shows the last 20 appoved Rfps.
	 * The limit is set at params file as rsslimit.
	 */
	public function actionRss2Rfp(){
		$result='';
		$org = Yii::app()->request->getParam('org');	
		$Organizations = new Organizations();
		$orgId = $Organizations->getOrgId($org);
		$error=0;
		$search=$name='';
		$Rfp = new Rfp();
		$data = $Rfp->getRfpListTable($search,$orgId,Yii::app()->params['agora']['rsslimit']);
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'rfp',
			'version'=>'rss2',
			'org'=>$org,
		));
				
	}
/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss1 type. Shows the last 20 appoved contracts.
	 * The limit is set at params file as rsslimit.
	 */
	public function actionRssContract(){
		$result='';
		$org = Yii::app()->request->getParam('org');	
		$Organizations = new Organizations();
		$orgId = $Organizations->getOrgId($org);
		$error=0;
		$search='';
		$model = new Contract();
		$data = $model->getContractListTable($search,$orgId,Yii::app()->params['agora']['rsslimit']);
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'contract',
			'version'=>'rss1',
			'org'=>$org,
		));
				
	}
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss2 type. Shows the last 20 appoved contracts.
	 * The limit is set at params file as rsslimit.
	 */
	public function actionRss2Contract(){
		$result='';
		$org = Yii::app()->request->getParam('org');	
		$Organizations = new Organizations();
		$orgId = $Organizations->getOrgId($org);
		$error=0;
		$search=$name='';
		$model = new Contract();
		$data = $model->getContractListTable($search,$orgId,Yii::app()->params['agora']['rsslimit']);
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'contract',
			'version'=>'rss2',
			'org'=>$org,
		));
				
	}	
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss1 type. Shows the last 20 appoved contracts.
	 * The limit is set at params file as rsslimit.
	 */
	public function actionRssPayments(){
		$result='';
		$org = Yii::app()->request->getParam('org');	
		$Organizations = new Organizations();
		$orgId = $Organizations->getOrgId($org);
		$error=0;
		$search='';
		$model = new Payments();
		$data = $model->getPaymentList($search,$orgId,Yii::app()->params['agora']['rsslimit']);
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'payments',
			'version'=>'rss1',
			'org'=>$org,
		));
				
	}
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss2 type. Shows the last 20 appoved contracts.
	 * The limit is set at params file as rsslimit.
	 */
	public function actionRss2Payments(){
		$result='';
		$org = Yii::app()->request->getParam('org');	
		$Organizations = new Organizations();
		$orgId = $Organizations->getOrgId($org);
		$error=0;
		$search=$name='';
		$model = new Payments();
		$data = $model->getPaymentList($search,$orgId,Yii::app()->params['agora']['rsslimit']);
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'payments',
			'version'=>'rss2',
			'org'=>$org,
		));
						
	}	
}
