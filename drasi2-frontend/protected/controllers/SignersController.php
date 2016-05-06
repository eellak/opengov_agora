<?php
class SignersController extends AgoraController
{

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
}