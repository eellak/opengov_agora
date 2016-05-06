<?php

class ContractitemController extends Controller{

	//the layout of the controller.
	//The contractItem is not used alone at the momment
	public $layout='//layouts/column2';

	/**
	 * The accessControl is set
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
	 * The main actions are createitem,updateitem.
	 * Everyone is allowed to do this.
	 * This method is used by the 'accessControl' filter.
	 * @return array access control rules
	 */
	public function accessRules()
	{
		return array(
			array('allow',  // allow all users to perform 'index' and 'view' actions
				'actions'=>array('createitem','updateitem'),
				'users'=>array('*'),
			),
			
		);
	}
	
	/*
	 * This creates an contractitem.
	 */
	public function actionCreateItem()
	{
		if(!Yii::app()->user->isGuest){
			$model=new Contractitem();
			//$contractItem = new Contractitem();
			
			// Uncomment the following line if AJAX validation is needed
			//$this->performAjaxValidation($model);
			//$this->performAjaxValidation($contractItem);
	
			if(isset($_POST['Contractitem']) )
			{
				$model->attributes=$_POST['Contractitem'];
			
				$valid = $model->validate();
				
				if($valid){
					$data = $model->saveToApi();
					if($data)
						$this->redirect(array('indexproc'));
				}	
			}
			$this->render('create',array(
				'model'=>$model,
			));
		}else 			
			Yii::app()->request->redirect(Yii::app()->user->returnUrl);
		
	}
	
	/**
	 * Returns the data model based on the id of the procurement or the contract.
	 * If the data model is not found, an HTTP exception will be raised.
	 * @param integer the ID of the model to be loaded
	 */
	public function loadModel($id)
	{
		$model=new Contractitem();
		$model=$model->findByPk($id);
		if($model===null)
			throw new CHttpException(404,'The requested page does not exist.');
		return $model;
	}
	
	public function getErros(){
    	
    }
	
}
