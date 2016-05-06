<?php
class jqAutocomplete extends CComponent
{
	
	public function __construct($params = array()) {
		foreach ($params as $p => $v) $this->$p = $v;
	}
	
	protected static function registerScript(){
		$assets 	= dirname(__FILE__) . '/jqautocomplete';
		$baseUrl 	= Yii::app()->assetManager->publish($assets);

		$cs = Yii::app()->getClientScript();
		$cs->registerCoreScript('jquery');
		$cs->registerScriptFile($baseUrl . '/jqautocomplete.js', CClientScript::POS_HEAD);
		$cs->registerCssFile($baseUrl . '/css/jqautocomplete.css');
	}
	
	protected static function create($selector, $options = array(), $position = CClientScript::POS_END){
		self::registerScript();
		$options = CJavaScript::encode(array_merge($options,self::defaultOptions()));
		
		Yii::app()->clientScript->registerScript(__CLASS__.$selector, 'jQuery.autoComplete("'.$selector.'",'. $options.');', $position);
	}
	
	public function addAutocomplete($selector, $opts = array()) {
		
		self::create($selector, array_merge($opts,self::defaultOptions()));
	}
	
	protected static function defaultOptions(){
		$config = array(
			'json'		=> true,		// is it JSON or XML?
			'valueSep'	=> ';',			// separator for different values
			'minChars'	=> '1',			// minimum chars typed by user before suggestion list is shown
			'meth'		=> 'get', 		// AJAX method (get/post)
			'varName'	=> 'input',		// variable name in the query string of the url
			'className' => 'autocomplete',	// classname of the list
			'timeout'	=> 3000,		// idle time before hiding the list, set to 0 to disable the timeout
			'delay'		=> 500,			// delay before showing the list
			'offsetY'	=> 0,			// y offset of the list
			'showNoResults'	=> true,	// show a message when no results were found
			'noResults'	=> 'No results were found',	// the message when no results were found
			'showMoreResults' => false,	// show a message when more results were found than the maximum
			'moreResults'	=> 'More results were found',	// the message when more results were found than the maximum
			'cache'		=> true,		// cache the received data
			'maxEntries'=> 25,			// maximum number of suggestions cached
			'maxResults'=> 25,			// maximum number of suggestions shown in the list
			'onAjaxError'	=> null,	// function to execute when an AJAX error occurred
			'maxHeight'	=> 0,			// maximum height of the list, set to 0 to disable maximum height
			'setWidth'	=> false,		// set to true to define a max/min width
			'minWidth'	=> 100,			// the minimum width of the list when setWidth is true
			'maxWidth'	=> 200,			// the maximum width of the list when setWidth is true
			'useNotifier'	=> true,	// use an icon notifier (spinner) between AJAX calls?
			'showAnimProperties' => array( // show animation options, see http://api.jquery.com/animate/ 
				'height'=>'100%'		// note: width and height settings will be animated when set, but will be overruled with calculated settings
				),
			'showAnimSpeed'	=> 'slow',	// the duration of the show animation, see http://api.jquery.com/animate/
			'showAnimEasing'	=> 'swing',// The easing function to use for the show animation 
										// (linear or swing when using jquery only, see http://gsgd.co.uk/sandbox/jquery/easing/ for more easing functions)
			'hideAnimProperties' => array(
				'height'=>'0'			// hide animation options, see http://api.jquery.com/animate/
				),
			'hideAnimSpeed'	=> 'slow', // hide animation duration, see http://api.jquery.com/animate/
			'hideAnimEasing'=> 'swing' // The easing function to use for the hide animation 
									// (linear or swing when using jquery only, see http://gsgd.co.uk/sandbox/jquery/easing/ for more easing functions)
		);	
		
		return $config;
	}
       
}