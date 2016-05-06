<?php

class SiteController extends Controller
{
	
		public $layout='//layouts/column2';
	
	/**
	 * Declares class-based actions.
	 */
	public function actions()
	{
		return array(
			// captcha action renders the CAPTCHA image displayed on the contact page
			'captcha'=>array(
				'class'=>'CCaptchaAction',
				'minLength'=> 4,
				'maxLength'=> 4, 
				'backColor'=>0xFFFFFF,
				'testLimit'=>0,
			),
			// page action renders "static" pages stored under 'protected/views/site/pages'
			// They can be accessed via: index.php?r=site/page&view=FileName
			'page'=>array(
				'class'=>'CViewAction', 
			),
			'errorForm'=>array(),
		);
	}
	
	/**
	 * @return array action filters
	 */
	public function filters()
	{
		return array(
			'accessControl', // perform access control for operations
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
				'actions'=>array('index','view','login','logout','page','captcha','error','errorForm','contactUs',
								'trainingMaterial','faq','rss','licence'),
				'users'=>array('*'),
			),
			array('allow', // allow admin user to perform 'admin' and 'delete' actions
				'actions'=>array('contact','userprofile','error','errorForm','contactUs'),
				'roles'=>array('admin','authenticated','superadmin'),
			),
			array('deny',  // deny all users
				'users'=>array('*'),
			),
		);
	}
	
	/**
	 * This is the default 'index' action that is invoked
	 * when an action is not explicitly requested by users.
	 */
	public function actionIndex()
	{
		// renders the view file 'protected/views/site/index.php'
		// using the default layout 'protected/views/layouts/main.php'
		$this->render('index');
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
	 * Displays the contact page
	 */
	public function actionContact()
	{
		$model=new ContactForm;
		if(isset($_POST['ContactForm']))
		{
			$model->attributes=$_POST['ContactForm'];
			if($model->validate())
			{
				$headers="From: {$model->email}\r\nReply-To: {$model->email}";
				mail(Yii::app()->params['adminEmail'],$model->subject,$model->body,$headers);
				Yii::app()->user->setFlash('contact','Thank you for contacting us. We will respond to you as soon as possible.');
				$this->refresh();
			}
		}
		$this->render('contact',array('model'=>$model));
	}

	/**
	 * Displays the login page
	 */
	public function actionLogin()
	{
		$model=new LoginForm;

		// if it is ajax validation request
		if(isset($_POST['ajax']) && $_POST['ajax']==='login-form')
		{
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}

		// collect user input data
		if(isset($_POST['LoginForm']))
		{
			$model->attributes=$_POST['LoginForm'];
			// validate user input and redirect to the previous page if valid
			//$model->login();
			if($model->validate() && $model->login())
				$this->redirect(array("site/userprofile"));	
			//$this->redirect(Yii::app()->user->returnUrl);
		}
		// display the login form
		$this->render('login',array('model'=>$model));
	}
	
	/**
	 * Displays the UserProfile page
	 */
	public function actionUserProfile()
	{
		
		if(isset(Yii::app()->user->isAdmin) && Yii::app()->user->isAdmin){
			$Item = new Procurement();
			$numOfProcurements = $Item->GetNumberOfProcurement();
			$Contract = new Contract();
			$numOfContracts = $Contract->GetNumberOfContracts();
			$Payment = new Payments();
			$numOfPayments = $Payment->GetNumberOfPayments();
			$Rfp = new Rfp();
			$numOfRfp = $Rfp->GetNumberOfRfp();
			$this->render('profile',array('numOfProcurements'=>$numOfProcurements,
										'numOfContracts'=>$numOfContracts,
										'numOfRfp'=>$numOfRfp,
										'numOfPayments'=>$numOfPayments));		
		}else
			throw new CHttpException(404,'The specified page cannot be found.');
	}

	/**
	 * A simple page with contactUs information.
	 * It mainly forwards to the ticketing system forms. 
	 */
	public function actionContactUs()
	{
		
			$this->render('contactus');		
	}
	/**
	 * A simple page with Training material information.
	 */
	public function actionTrainingMaterial()
	{
		
			$this->render('trainingMaterial');		
	}
	
/**
	 * A simple page with FAQ information.
	 */
	public function actionFaq()
	{
		
		$this->render('faq');
	}
	/**
	 * A simple page with rss information.
	 */
	public function actionRss()
	{
		
			$this->render('rss');		
	}
	/**
	 * A simple page with licencing information.
	 */
	public function actionLicence()
	{
		
		$this->render('licence');
	}
	
	/**
	 * Logs out the current user and redirect to homepage.
	 */
	public function actionLogout()
	{
	    $assigned_roles = Yii::app()->authManager->getRoles(Yii::app()->user->id); //obtains all assigned roles for this user id
	    if(!empty($assigned_roles)) //checks that there are assigned roles
	    {
	        $auth=Yii::app()->authManager; //initializes the authManager
	        foreach($assigned_roles as $n=>$role)
	        {
	            if($auth->revoke($n,Yii::app()->user->id)) //remove each assigned role for this user
	                Yii::app()->authManager->save(); //again always save the result
	        }
	    }
	 
	    Yii::app()->user->logout(); //logout the user
	    $this->redirect(Yii::app()->homeUrl); //redirect the user
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
	    $this->render($view, $data);
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
	 * Displays an error page.
	 * The success page content is specified via the flash data '_error_'
	 * which is generated by {@link Controller::error()} method.
	 * If the flash data does not exist, it will redirect the browser to the homepage.
	 */
	public function actionErrorForm()
	{
	    if (!Yii::app()->user->hasFlash('_error_'))
	        $this->redirect(Yii::app()->homeUrl);
	    $data = Yii::app()->user->getFlash('_error_');
	    
	    $view = $data['_view_'];
	  
	    unset($data['_view_']);
	    $this->render($view,array('data'=>$data));
	}
	
	
	
	
}