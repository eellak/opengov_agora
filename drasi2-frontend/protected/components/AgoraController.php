<?php
/**
 * Agora Controller is a customized classes for agora project.
 * It mainly deals with flash messages in order to have a central point of displaying messages.
 * Flash messages are essentially stored as session data. As a result, they will be serialized 
 * when saving and deserialized when loading. For this reason, the data saved as flash message 
 * should be simple data, such as strings, numbers. Do not store objects (e.g. data model objects) 
 * or resource handles in it because their serialization and deserialization may cause problem.
 * @author themiszamani
 *
 */
class AgoraController extends Controller {
	
	//since all of my controllers used the same layout file, i added it to the AgoraController class, 
	//allowing us to remove it from all the individual controllers
	public $layout='//layouts/column2';
	
	/**
	 * Stores a flash message. A flash message is available only in the current and the next requests.
	 * @param string $key key identifying the flash message
	 * @param mixed $value the actual flash message
	 * @param mixed $defaultValue if this value is the same as the flash message, the flash message will be removed. (Therefore, you can use setFlash('key',null) to remove a flash message.)
	 */
	public function setFlash($key, $value, $defaultValue=NULL)
    {
        Yii::app()->user->setFlash($key, $value, $defaultValue);
    }
 
    /**
	 * Stores a success flash message. A flash message is available only in the current and the next requests.
	 * @param mixed $value the actual flash message
	 * @param mixed $defaultValue if this value is the same as the success flash message, the flash message will be removed. (Therefore, you can use setFlash('key',null) to remove a flash message.)
	 */
    public function setFlashSuccess($value, $defaultValue = null)
    {
        $this->setFlash('success', $value, $defaultValue);
    }
    
    /**
	 * Stores an error flash message. A flash message is available only in the current and the next requests.
	 * @param mixed $value the actual flash message
	 * @param mixed $defaultValue if this value is the same as the error flash message, the flash message will be removed. (Therefore, you can use setFlash('key',null) to remove a flash message.)
	 */
    public function setFlashError($value, $defaultValue = null)
    {
        $this->setFlash('error', $value, $defaultValue);
    }
	
	/**
	 * Renders an error view.
	 * Note that this method will redirect the browser to the 'site/error' route
	 * which will then render the specified success view.
	 * @param string $view the view name. It can be a view relative to
	 * the current controller or an absolute view (starting with '/')
	 * @param array $data the data to be passed to the view.
	 */
	public function errorProc($view, $code,$message)
		{
	    if($view[0]!=='/')  // relative to current controller
	        $view = '/' . $this->id . '/' . $view;
	    $data['_view_'] = $view;
	    $data['code']=$code;
	    $data['message']=$message;

	    Yii::app()->user->setFlash('_error_', $data);
	    
	    
	    $this->redirect(array('site/errorForm'),$data);
	}
	/**
	 * Renders a success view.
	 * Note that this method will redirect the browser to the 'site/success' route
	 * which will then render the specified success view.
	 * @param string $view the view name. It can be a view relative to
	 * the current controller or an absolute view (starting with '/')
	 * @param array $data the data to be passed to the view.
	 */
	public function success($view, $data=array())
	{
	    if($view[0]!=='/')  // relative to current controller
	        $view = '/' . $this->id . '/' . $view;
	    $data['_view_'] = $view;
	    Yii::app()->user->setFlash('_success_', $data);
	    
	    $this->redirect(array('procurement/success'),$data);
	}
	/**
	 * Renders a success view.
	 * Note that this method will redirect the browser to the 'site/success' route
	 * which will then render the specified success view.
	 * @param string $view the view name. It can be a view relative to
	 * the current controller or an absolute view (starting with '/')
	 * @param array $data the data to be passed to the view.
	 */
	public function successContract($view, $data=array())
	{
	    if($view[0]!=='/')  // relative to current controller
	        $view = '/' . $this->id . '/' . $view;
	    $data['_view_'] = $view;
	    Yii::app()->user->setFlash('_success_', $data);
	    
	  	$this->redirect(array('contract/success'),$data);
	}
	
	/**
	 * Renders a success view.
	 * Note that this method will redirect the browser to the 'procurement/success' route
	 * which will then render the specified success view.
	 * @param string $view the view name. It can be a view relative to
	 * the current controller or an absolute view (starting with '/')
	 * @param array $data the data to be passed to the view.
	 */
	public function successProc($view, $data=array())
	{
	    if($view[0]!=='/')  // relative to current controller
	        $view = '/' . $this->id . '/' . $view;
	    $data['_view_'] = $view;
	    Yii::app()->user->setFlash('_success_', $data);
	    
	  	$this->redirect(array('procurement/success'),$data);
	}
	
/**
	 * Renders a success view.
	 * Note that this method will redirect the browser to the 'site/success' route
	 * which will then render the specified success view.
	 * @param string $view the view name. It can be a view relative to
	 * the current controller or an absolute view (starting with '/')
	 * @param array $data the data to be passed to the view.
	 */
	public function successRfp($view, $data=array())
	{
	    if($view[0]!=='/')  // relative to current controller
	        $view = '/' . $this->id . '/' . $view;
	    $data['_view_'] = $view;
	    Yii::app()->user->setFlash('_success_', $data);
	    
	  	$this->redirect(array('rfp/success'),$data);
	}
	
	/**
	 * Renders a success view.
	 * Note that this method will redirect the browser to the 'payment/success' route
	 * which will then render the specified success view.
	 * @param string $view the view name. It can be a view relative to
	 * the current controller or an absolute view (starting with '/')
	 * @param array $data the data to be passed to the view.
	 */
	public function successPayment($view, $data=array())
	{
	    if($view[0]!=='/')  // relative to current controller
	        $view = '/' . $this->id . '/' . $view;
	    $data['_view_'] = $view;
	    Yii::app()->user->setFlash('_success_', $data);
	    
	  	$this->redirect(array('payments/success'),$data);
	}
	
	/*I decided to replace render() within the whole project to use Content Compactors
	 * Content Compacter removes unnecessary whitespace, intendents and rowbreaks
	 * in the rendered viewfiles (HTML). It also compresses inlined CSS and JS 
	 * in the HTML using borrowed code from minify. It can also remove HTML 
	 * conditionals specific to IE when detected browser isn't IE.
	 * It is based on http://www.yiiframework.com/extension/contentcompactor#hh3 component
	 */
	public function render($view, $data = null, $return = false, $options = null)
	{
	    $output = parent::render($view, $data, true);
	 
	    $compactor = Yii::app()->contentCompactor;
	    if($compactor == null)
	        throw new CHttpException(500, Yii::t('messages', 'Missing component ContentCompactor in configuration.'));
	 
	    $output = $compactor->compact($output, $options);
	 
	    if($return)
	        return $output;
	    else
	        echo $output;
	}
	function __sleep(){
	
	}
	
	 public function actionError()     {        
    		 if($error=Yii::app()->errorHandler->error)         {            
    		 	 if(Yii::app()->request->isAjaxRequest)                 
    		 	 	echo $error['message'];             
    		 	 else                 
    		 	 	$this->render('//error', $error);         }    
     } 
}
