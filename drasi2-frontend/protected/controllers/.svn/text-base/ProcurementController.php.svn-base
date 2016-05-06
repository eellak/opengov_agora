<?php
/**
 * 
 * This is the Contoller of Procurement. 
 * It extends AgoraController so as to have our functions mainly 
 * for Messages. 
 * The default functions of Procurement Controller is
 * a) to perform access controls.
 * b) to specify the access controlo rules. 
 * The main functions of Procurement Controller is
 * a) View  list of procurements
 * b) Create Procurement 
 * c) Update Procurement
 * d) Download Procurement File
 * @author themiszamani
 *
 */
class ProcurementController extends AgoraController
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
				'actions'=>array('indexproc','indexprocperorg','viewproc','downloadproc','dynamicsigners',
								'createPDF','procApprovals','procApprovalsSub','searchproc','cpvcodes',
								'Dynamicforeis','Check','rss','rss2','atom'),
				'users'=>array('*'),
			),
			array('allow', // allow authenticated user to perform 'create' and 'update' actions
				'actions'=>array('createproc','viewproc','indexproc','success','cancelproc'),
				'roles'=>array('admin','authenticated'),
			),
			array('allow',
				'actions'=>array('viewproc','indexproc','success','updateProc','deleteproc','cancelproc'),
				'roles'=>array('superadmin'),
			),
			array('allow', // allow admin user to perform 'admin' and 'delete' actions
				'actions'=>array('createproc','updateProc','ApproveRequest','viewproc','indexproc','deleteproc','cancelproc'),
				'roles'=>array('admin'),
			),
			array('deny',  // deny all users
				'users'=>array('*'),
			),
		);
	}

	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss1 type. Shows the last 20 appoved procurements.
	 */
	public function actionRss(){
		$model = new Procurement();
		$error=0;
		$search=$name='';
		$search='approval';	$name='type';
		$data = $model->getProcurementList($search,$name,'',Yii::app()->params['agora']['rsslimit']);
		
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'procurement',
			'version'=>'rss1',
		));	
	}
	/**
	 * RSS function. This function is responsible for creating 
	 * rss feed of rss2 type. Shows the last 20 appoved procurements.
	 */
	public function actionRss2(){
		$model = new Procurement();
		$error=0;
		$search=$name='';
		$search='approval';	$name='type';
		$data = $model->getProcurementList($search,$name,'',Yii::app()->params['agora']['rsslimit']);
		
		$this->render('/site/rssdata',array(	
			'data'=>$data,
			'type'=>'procurement',
			'version'=>'rss2',
		));
		
	}
	
	/**
	 * The basic function that indexes the first 100 procurements.
	 * It shows the basic data of the procurement such as
	 * title, ADA, all dates.
	 * @param string $search the ada search
	 * @param string $name one of the procurements type (approval, isapproved, notapproved)
 	 */
	public function actionIndexproc($search='notapproved',$name='type')
	{
		$result='';
		$model = new Procurement();
		$error=0;
		$dataProvider=array();
	//	$search="ldsas";
		if($search!='notapproved' && $search!='approved' && $search!='approval') $name='search';
		$data = $model->getProcurementList($search,$name);
		$dataProvider = new CArrayDataProvider($data, array(
		    	'keyField' => 'documentUrl',     // Agent primary key here
			));
		$this->render('index',array(	
			'dataProvider'=>$dataProvider,
		));
		
	}
	
	function actionSearchproc(){
		$model = new Procurement();
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
			if(isset($_POST['Procurement']['title'])){
		    	$title = $_POST['Procurement']['title'];
		    }else $title='';
		    $model->title=$title;      
		    $session = Yii::app()->getComponent('session');
		    $session->add('title',$title);

		    //foreis - ypoforeis 
		    if (Yii::app()->user->isGuest){
			    if(isset($_POST['Procurement']['foreis'])){
			    	$foreis = $_POST['Procurement']['foreis'];
			    }else $foreis='';
			    
			     if(isset($_POST['Procurement']['ypoforeis'])){
			    	$ypoforeis = $_POST['Procurement']['ypoforeis'];
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
		    if(isset($_POST['Procurement']['cpvsid']) && $_POST['Procurement']['cpvsid']!=''){
				$cpv=$_POST['Procurement']['cpvsid'];
					
			}else $cpv='';
			$model->cpvsid=$cpv;
			$session = Yii::app()->getComponent('session');
		    $session->add('cpv',$cpv);
			
		    if(isset($_POST['Procurement']['documentUrl'])){
		    	$ada = $_POST['Procurement']['documentUrl'];
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
			$cost= $data[0]['totalCostBeforeVAT'];
			$fm = new CNumberFormatter(Yii::app()->getLocale());
			$cost =$fm->formatCurrency($cost,'EUR');
		$this->render('search',array(	
				'dataProvider'=>$dataProvider,
				'cost'=>$cost,
				'model'=>$model
			));
	
	}
	

	public function excelexportx($dataProvider){
		$objPHPExcel = Yii::app()->excel;
		$styleArray = array(
				'font' => array(
					'bold' => true,
		
				),
				'alignment' => array(
					'horizontal' => PHPExcel_Style_Alignment::HORIZONTAL_CENTER,
				),
				'borders' => array(
					'top' => array(
						'style' => PHPExcel_Style_Border::BORDER_THIN,
					),
					'bottom' => array(
						'style' => PHPExcel_Style_Border::BORDER_THIN,
					),
					'right' => array(
						'style' => PHPExcel_Style_Border::BORDER_THIN,
					),
					'left' => array(
						'style' => PHPExcel_Style_Border::BORDER_THIN,
					),
				),
				'fill' => array(
					'type' => PHPExcel_Style_Fill::FILL_GRADIENT_LINEAR,
					'rotation' => 90,
					'startcolor' => array(
						'argb' => 'FFA0A0A0',
					),
					'endcolor' => array(
						'argb' => 'FFFFFFFF',
					),
				),
			);
	    //create the labels of excel
		$objPHPExcel->getActiveSheet()->setCellValue('A1',Yii::t('procurement','title'));
        $objPHPExcel->getActiveSheet()->setCellValue('B1',Yii::t('procurement','uniqueDocumentCode'));
        $objPHPExcel->getActiveSheet()->setCellValue('C1',Yii::t('procurement','dateSigned'));
        $objPHPExcel->getActiveSheet()->setCellValue('D1',Yii::t('form','Created'));
        $objPHPExcel->getActiveSheet()->setCellValue('E1',Yii::t('form','LastUpdated'));
        $objPHPExcel->getActiveSheet()->setCellValue('F1',Yii::t('item','descriptionS'));
        $objPHPExcel->getActiveSheet()->setCellValue('G1',Yii::t('item','cost'));
        $objPHPExcel->getActiveSheet()->setCellValue('H1',Yii::t('form','approves'));
        
        $objPHPExcel->getActiveSheet()->getStyle('A1:H1')->applyFromArray($styleArray);
        
         //Set value for each cell
         $i=2;
         
         for($j=0;$j<count($dataProvider->rawData);$j++){
         	$objPHPExcel->getActiveSheet()->setCellValue('A'. $i,$dataProvider->rawData[$j]['title']);
	        $objPHPExcel->getActiveSheet()->setCellValue('B'. $i,$dataProvider->rawData[$j]['documentUrl']);
	        $objPHPExcel->getActiveSheet()->setCellValue('C'. $i,$dataProvider->rawData[$j]['dateSigned']);
	        $objPHPExcel->getActiveSheet()->setCellValue('D'. $i,$dataProvider->rawData[$j]['submissionTime']);
	        $objPHPExcel->getActiveSheet()->setCellValue('E'. $i,$dataProvider->rawData[$j]['lastModifiedTime']);
	        $objPHPExcel->getActiveSheet()->setCellValue('F'. $i,$dataProvider->rawData[$j]['description']);
	        $objPHPExcel->getActiveSheet()->setCellValue('G'. $i,$dataProvider->rawData[$j]['costBeforeVat']);
	        $objPHPExcel->getActiveSheet()->setCellValue('H'. $i,$dataProvider->rawData[$j]['approvesRequest']);
	        
	        $i++;
          }
          
        //Set width for each cell 
        $objPHPExcel->getActiveSheet()->getColumnDimension('A')->setWidth(24);
        $objPHPExcel->getActiveSheet()->getColumnDimension('B')->setWidth(24);
     	$objPHPExcel->getActiveSheet()->getColumnDimension('C')->setWidth(18);
        $objPHPExcel->getActiveSheet()->getColumnDimension('D')->setWidth(18);
        $objPHPExcel->getActiveSheet()->getColumnDimension('E')->setWidth(18);
        $objPHPExcel->getActiveSheet()->getColumnDimension('F')->setWidth(30);  
        $objPHPExcel->getActiveSheet()->getColumnDimension('G')->setWidth(24);  
        $objPHPExcel->getActiveSheet()->getColumnDimension('H')->setWidth(24);  
        
        return $objPHPExcel;
      }
      
      function createXLS($objPHPExcel){
      	  //file name
      	$file = 'proc'.date("Ymdgiu").'.xlsx';
        //$filename = "/var/www/agora_front/protected/data/files/$file";
      
        $Path = str_replace("index.php", "", Yii::app()->request->scriptFile);
		$filename = $Path."protected/data/files/".$file;
        // You can use like PHPExcel documentation from codeplex: 
        $objWriter = new PHPExcel_Writer_Excel2007($objPHPExcel);
        $objWriter->save($filename);
        
        echo"<div class=\"item1\" style='padding-left:200px;padding-top:-50px;margin-top:-10px;'>";
          echo"<div class=\"contract\">";
        		echo "<div style='padding-left:50px;'>";
        		echo"<span>";
        			echo Yii::t('yii','download xls file');
        		echo"</span> ";
        		echo" <a href='protected/data/files/$file'>XLS</a>";  
        		echo"</div>";
        		echo"</div>";
        echo"</div>";
      }
	  
	
	/**
	 * Displays a particular model.
	 * @param integer $id the ID of the model to be displayed
	 */
	public function actionViewProc($documentUrl)
	{
		$data = new Procurement();
		$data->findByPk($documentUrl);
		//wrong documentUrl
		if($data!=''){
			if(!is_null($data->documentUrl) && !is_null( $data->title ) ) {		
				$ContractItem = new Contractitem();
				$ContractData = $ContractItem->findByPk($documentUrl,'procurement');
				$data->OrganizationIdRefUnits=	Units::item('Units',$data->OrganizationIdRefUnits,$data->OrganizationIdRef);
				$data->signers =Units::item('Signers',$data->signers,$data->OrganizationIdRef);
				$data->awardProcedure = Lookup::item('award_procedure', $data->awardProcedure);				
				$data->document = "<a href='index.php?r=procurement/downloadproc&documentUrl=".$data->documentUrl.">Download</a>";
						
				$oldData = new Procurement();
				$ContractItemData='';
				if($data->isApproved!='') {
					$oldData->findByPk($data->isApproved);
					$oldData->OrganizationIdRefUnits=	Units::item('Units',$oldData->OrganizationIdRefUnits,$oldData->OrganizationIdRef);
					$oldData->signers =Units::item('Signers',$oldData->signers,$oldData->OrganizationIdRef);
					$ContractItemApproved = new Contractitem();
					$ContractItemData = $ContractItemApproved->findByPk($data->isApproved,'procurement');
				}
				$this->render('view',array(
							'model'=>$data,
							'ContractData'=>$ContractData,
							'oldData' => $oldData,
							'ContractItemData'=>$ContractItemData,
						));
			}else 
				$this->render('error',array('code'=>Yii::t('procurement','noview'),'message'=>Yii::t('procurement','noviewmessage')));		
		}else 
				$this->render('error',array('code'=>Yii::t('procurement','noview'),'message'=>Yii::t('procurement','noviewmessage')));		
		
	}
	
	/**
	 * This is the function for the download of file
	 * In order to download a file you should always make 
	 * a CURL request to API (Apiconnector). 
	 * Then we save the file to /protected/data/files in order to create the 
	 * necessary headers for the download part (filedownload). 
	 * Whilst the file is downloaded we call filedownload in 
	 * order to pass the file to users. (components/filedownload)	 */
	public function actionDownloadproc(){
			$docURI = $_GET['documentUrl'];
			$file = "proc-".$docURI.".pdf";
			
			//Connect to ApiConnector
			//sets the config table for the http request. 
			$config['apirequest'] ='procurementDownload';
			$config['documentid'] =$docURI;
			
			//send a curl request for the document 
			$api = new Apiconnector($config);
			$output = $api->makeRequest();
			
			//Create headers to pass the file to the final user.
			$download = new filedownload($output,"files",$file);
	}
	
	
	/**
	 * Creates a new model.
	 * If creation is successful, the browser will be redirected to the 'view' page.
	 */
	public function actionCreateProc()
	{
		
		if(!Yii::app()->user->isGuest){
			$model=new Procurement;
			$model->OrganizationIdRef=Yii::app()->user->RefId;
			$model->scenario='createproc';
			//$contractItem = new Contractitem();
			$contractitemmanager=new ContractItemManager();
			
			// Uncomment the following line if AJAX validation is needed
			$this->performAjaxValidation($model);
		
			if(isset($_POST['Procurement']) && isset($_POST['Contractitem']))
			{
				
				$model->attributes=$_POST['Procurement'];
				$contractItem->attributes=$_POST['Contractitem'];
				if(isset($_POST['Contractitem']))
					$contractitemmanager->manage($_POST['Contractitem']);
				else
					Yii::app()->user->addFlash(Yii::t('form','noContractItem'),'error');
	
				$valid = $model->validate();
				$valid = $contractitemmanager->validate($model) && $valid;
				
				if($valid){
					
					$data = $model->save();
					if(is_array($data) && !isset($data[0]['error'])){
						$this->successProc('procurementSuccess', array('model'=>$model,
									   'documentUrl'=>$data[0]['id'],'title'=>$model->title,
								       'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'create'));
						//$this->redirect(array('indexproc'));
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
			//	'contractItem'=>$contractItem,
			   'contractitemmanager'=>$contractitemmanager,
				'modelname'=>'procurement',
			
			));
		}else 			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
		
	}
	
	/**
	 * Updates a particular model.
	 * If update is successful, the browser will be redirected to the 'view' page.
	 * @param integer $id the ID of the model to be updated
	 */
	public function actionUpdateProc($documentUrl)
	{
		//http://e-mats.org/2008/07/fatal-error-exception-thrown-without-a-stack-frame-in-unknown-on-line-0/
		$documentUrl = Yii::app()->request->getParam('documentUrl');	
		$model= new Procurement();
		$model->findByPk($documentUrl);
		$model->scenario="update";
		$model->setScenario("update");

		//for access for the next 24 hours
		if( (!$model->actionPermitted)){
			$this->render('error',array('code'=>Yii::t('procurement','noAction'),'message'=>Yii::t('procurement','noActionmessage')));
			
		}//check if the procurement has been cancelled
		else if ($model->cancelled=='true')
			$this->render('notice',array('code'=>Yii::t('procurement','cancelled'),'message'=>Yii::t('procurement','cancelledmessage')));
		//the procuurement Request has been approved so no changes are accepted.
		else if(($model->isApproved!='')){ 
			$this->render('notice',array('code'=>Yii::t('procurement','noApproveUpdate'),'message'=>Yii::t('procurement','noApproveUpdatemessage')));
		}//else update the data
		else{
			//wrong $documentUrl
			if((!is_null($model->documentUrl) && !is_null( $model->title )) ) {
				
			$contractItem = new Contractitem();
			$contractModel = $contractItem->findByPk($documentUrl,'procurement');
			$contractitemmanager=ContractItemManager::load($contractModel);
			
			// Uncomment the following line if AJAX validation is needed
			$this->performAjaxValidation($model);
						
			if(isset($_POST['Procurement'])){
				$model->attributes=$_POST['Procurement'];
				$valid = $model->validate();

				if(isset($_POST['Contractitem'])){
					$contractitemmanager->manage($_POST['Contractitem']);
				}else
					$model->addError('form',Yii::t('form','noContractItem'));
				
				$valid = $contractitemmanager->validate() && $valid;
				if($valid){
						$saved = $model->save('update');
						
						if(is_array($saved) && !isset($saved[0]['error'])){
							$this->successProc('procurementSuccess',array('documentUrl'=>$model->documentUrl,'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'update'));
						}else if(is_array($saved) && isset($saved[0]['error'])) {
						
						for($i=0;$i<count($saved[0]['error']);$i++){
							if($saved[0]['error'][$i]=='approvedRequest') $saved[0]['error'][$i]='updateApprovedRequest';
							$model->addError('form',Yii::t('form',$saved[0]['error'][$i]));
						}
					}else 
						$model->addError('form',Yii::t('form','no middleware'));
				}
			}
				$this->render('update',array(
					'model'=>$model,
			    	'contractitemmanager'=>$contractitemmanager,
					'modelname'=>'procurement',
				));
			}else
				$this->render('error',array('code'=>Yii::t('procurement','noview'),'message'=>Yii::t('procurement','noviewmessage')));
		}//end actionPermitted
		
	}

	/**
	 * This function approves the request.
	 * The user may change the data of a procurement request and approve it.  
	 * @param string $documentUrl the unique document id
	 */
	public function actionApproveRequest($documentUrl){
		
		$documentUrl = Yii::app()->request->getParam('documentUrl');	
		$modelAll = new Procurement();
		$modelAll->findByPk($documentUrl);
		//check if cancelled
		if ($modelAll->cancelled=='true')
				$this->render('notice',array('code'=>Yii::t('procurement','cancelled'),'message'=>Yii::t('procurement','cancelledmessage')));
		else if($modelAll->title=='') 
			$this->render('error',array('code'=>Yii::t('procurement','noview'),'message'=>Yii::t('procurement','noviewmessage')));
		else{	
		
		$model = new Procurement();
		$model->fulfilmentDate = $modelAll->fulfilmentDate;
		$model->documentUrl=$documentUrl;
		$model->scenario="approverequest";
		$model->setScenario("approverequest");
	
		
		$isApproved=$model->isApproved($documentUrl);
		
		//if the procurement is the new approved one no action is permitted
		if(($modelAll->approvesRequest!='')){
			$this->render('notice',array('code'=>Yii::t('procurement','noApprove'),
							'message'=>Yii::t('procurement','noApprovemessage')));
		}//if the procurement is the one being approved no action is permitted
		else if(($isApproved!='')){
			$this->render('notice',array('code'=>Yii::t('procurement','noApprove'),
							'message'=>Yii::t('procurement','noApprovemessage')));
		}else{
			//wrong $documentUrl
			if((!is_null($model->documentUrl))) {
				
			$contractItem = new Contractitem();
			$contractModel = $contractItem->findByPk($documentUrl,'procurement');
			if(!isset($contractModel->quantity[0])){
				$this->render('error',array('code'=>Yii::t('procurement','noview'),'message'=>Yii::t('procurement','noviewmessage')));
			}else{
				$contractitemmanager=ContractItemManager::load($contractModel);
					
			
			// Uncomment the following line if AJAX validation is needed
			$this->performAjaxValidation($model);
						
			if(isset($_POST['Procurement'])){
				$model->attributes=$_POST['Procurement'];
				$valid = $model->validate();

				if(isset($_POST['Contractitem'])){
					$contractitemmanager->manage($_POST['Contractitem']);
				}else
					$model->addError(Yii::t('form','noContractItem'),'error');
				$valid = $contractitemmanager->validate() && $valid;
				if($valid){
						$saved = $model->save('approverequest');
						if(is_array($saved) && !isset($saved[0]['error'])){
							$this->successProc('procurementSuccess',
								array('documentUrl'=>$saved[0]['id'],
									   'title'=>$model->title,
									   'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'approverequest'));
						}else{
							$errors = new ApiErrorHandling('procurement');
							for($i=0;$i<count($saved[0]['error']);$i++)
								$model->addError($errors->NameError($saved[0]['error'][$i]),$errors->DescriptionError($saved[0]['error'][$i]));	
						
						}
				}
				
			}
				$this->render('approverequest',array(
					'modelAll'=>$modelAll,
					'model'=>$model,
					//'contractItem'=>$contractItem,
			    	'contractitemmanager'=>$contractitemmanager,
					'modelname'=>'procurement',
				));
			}
			}else
				$this->render('error',array('code'=>Yii::t('procurement','noview'),'message'=>Yii::t('procurement','noviewmessage')));
		}//end actionPermitted
		}
		
	}
	
		
	
	/**
	 * Deletes a particular model.
	 * If deletion is successful, the browser will be redirected to the 'index' page.
	 * While deleting the following cases may appear
	 * a) Successful deletion --> $delete=1
	 * b) Unauthorized deletion --> $delete=403
	 * c) Trying to delete a procurement that doesnt exist --> $delete=404
	 * @param integer $documentUrl the ID of the model to be deleted
	 */
	public function actionDeleteProc($documentUrl)
	{
		$model=new Procurement();
		$model->scenario='deleteproc';
		$model->findByPk($documentUrl);
		
		//print_r($model);
 		//$this->performAjaxValidation($model);
 		if( ($model->actionPermitted!=1))
			$this->render('error',array('code'=>Yii::t('procurement','noAction'),
							'message'=>Yii::t('procurement','noActionmessage')));
		else if(isset($_POST['Procurement']['Deldescription'])){
			$model->attributes=$_POST['Procurement'];
			$Procurement_Deldescription = Yii::app()->request->getParam('Procurement_Deldescription');
		
			if($_POST['Procurement']['Deldescription']==''){
				$valid = $model->validate();
			}else{
				$config['apirequest'] ='procurementDelete';
				$config['documentid'] =$documentUrl;
				$config['reason'] = $_POST['Procurement']['Deldescription'];
				$apiDelete = new Apiconnector($config);
				$delete = $apiDelete->delete($documentUrl,'procurement');
				//print_r($delete);
				if($delete==1){
					
					$this->successProc('procurementSuccess', array('documentUrl'=>$documentUrl,
						  	//'title'=>$model->title,'protocolNumberCode'=>$model->protocolNumberCode,
						   'deldescription'=>$_POST['Procurement']['Deldescription'],'action'=>'delete'));
			//		$this->successProc('procurementSuccess',
			//			array('documentUrl'=>$documentUrl,
			///				   'title'=>$model->title,
				//			   'protocolNumberCode'=>$model->protocolNumberCode,'action'=>'delete'));
				}else{
					$this->errorProc('errorForm',$delete,Yii::t('form','delete'.$delete));
				
				}
				
			}
		}
		 	$this->render('_delete', array('model'=>$model,'error'=>'dadsa'));
		
	   
	}
	
	/**
	 * Cancels a particular model.
	 * If cancelation is successful, the browser will be redirected to the 'index' page.
	 * While canceling the following cases may appear
	 * a) Successful cancelation --> $delete=1
	 * b) Unauthorized cancelation --> $delete=403
	 * c) Trying to cancel a procurement that doesnt exist --> $delete=404
	 * @param integer $documentUrl the ID of the model to be deleted
	 */
	public function actionCancelProc($documentUrl)
	{
		
		$model=new Procurement();
		$model->scenario='cancelproc';
		$model->findByPk($documentUrl);
		$cancel=0;

		if ($model->cancelled=='true')
				$this->render('notice',array('code'=>Yii::t('procurement','cancelled'),'message'=>Yii::t('procurement','cancelledmessage')));
		else if(isset($_POST['Procurement']['Deldescription'])){
			$model->attributes=$_POST['Procurement'];
			$Procurement_Deldescription = Yii::app()->request->getParam('Procurement_Deldescription');
		
			if($_POST['Procurement']['Deldescription']==''){
				$valid = $model->validate();
			}else{
				$config['apirequest'] ='procurementCancel';
				$config['documentid'] =$documentUrl;
				$ADACancel = $_POST['Procurement']['ADACancel'];
				$delCancel = $_POST['Procurement']['Deldescription'] ."---".$ADACancel."---";
				$config['reason'] = $delCancel;
				$config['cancellationType'] = $_POST['Procurement']['cancellationType'];
				$apiDelete = new Apiconnector($config);
				$cancel = $apiDelete->cancel($documentUrl,'procurement');
				//print_r($cancel);
				if($cancel==1){
					$this->successProc('procurementSuccess', array('documentUrl'=>$documentUrl,
						   'deldescription'=>$_POST['Procurement']['Deldescription'],'action'=>'cancel'));
				}else
					$this->errorProc('errorForm',$cancel,Yii::t('form','cancel'.$cancel));
			}
		}
		 	$this->render('_cancel', array('model'=>$model,'error'=>'dadsa'));
	}
     
	/**
	 * Manages all models.
	 */
	
	public function actionAdmin()
	{
		$model=new Procurement('search');
		$model->unsetAttributes();  // clear any default values
		if(isset($_GET['Procurement']))
			$model->attributes=$_GET['Procurement'];

		$this->render('admin',array(
			'model'=>$model,
		));
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
	 * http://www.yiiframework.com/forum/index.php?/topic/8641-urlmanager-rules-for-route-aliases/page__p__43204__hl__urlManager+mi+ingAction#entry43204
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
		$OrgNewRef = $_POST['Procurement']['OrganizationIdRefUnits'];
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
		$foreis = $_POST['Procurement']['foreis'];
		$data= ODE::items('withid',$foreis);
		
		foreach($data as $value=>$name)
			echo CHtml::tag('option',array('value'=>$value),CHtml::encode($name),true);
	}
	
	public function actionCheck(){
		
		if (Yii::app()->request->isAjaxRequest)
        {
		
		$RelatedADA = $_POST['ADA'];
        $ADAData = new Decision();
		$ADAResults = $ADAData->findByPK($RelatedADA);
		$dateSigned=$protocolNumberCode=$title=$OrganizationIdRefUnits=$signers=$RelatedDocument=$documentCSS=$ari8mosKAE=$documentURL='';
		$RelatedDocumentStatus = 0;
		
		if($ADAResults!=-1){
			$dateSigned = $ADAResults[0]['submissionTimestamp'];
			$protocolNumberCode = $ADAResults[0]['protocolNumber'];
			$title = $ADAResults[0]['title'];
			$OrganizationIdRefUnits = $ADAResults[0]['organizationUnitId'];
			$signers = $ADAResults[0]['signerId'];
			$dataStatus ="<img src='images/tick.png' width='24px' height='24px' />"; 
			$RelatedDocument = $ADAResults[0]['documentUrl'];
			$ari8mosKAE = $ADAResults[0]['ari8mosKAE'];
			
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
		 			'ari8mosKAE' => $ari8mosKAE,
		 
		 ));
                                        // exit;               
           Yii::app()->end();
        
        
           }
	
	}
	
	
}
