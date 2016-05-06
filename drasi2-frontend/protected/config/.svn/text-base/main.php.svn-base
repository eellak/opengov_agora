<?php
//different configuration files. With this you dont have 
// to change the db configuration file for each different
//installation.
/*$pre_config = 
    CMap::mergeArray(
       // require(dirname(__FILE__).'/db.php'),
        require(dirname(__FILE__).'/params.php')
        
);*/

// uncomment the following to define a path alias
// Yii::setPathOfAlias('local','path/to/local-folder');

// This is the main Web application configuration. Any writable
// CWebApplication properties can be configured here.
return CMap::mergeArray(require(dirname(__FILE__).'/params.php'), array(	
        'name'=>'Ηλεκτρονική Πλατφόρμα για την παρακολούθηση δημόσιων συμβάσεων ',

	'language' => 'el',
	//'homeUrl'=>array('organizations/indexall'),

	// preloading 'log' component
	'preload'=>array('log'),

	// autoloading model and component classes
	'import'=>array(
		'application.models.*',
		'application.components.*',
		'application.extensions.httpclient.*',
		'application.extensions.httpclient.adapter.*',
		'application.extensions.jqAutocomplete.*',
		'application.extensions.contentCompactor.*',
		'application.extensions.juiMsg.*',
		'application.extensions.phpexcel.*',
		'application.extensions.PHPExcel4Yii.*',
		'application.extensions.PHPExcel4Yii.PHPExcel*',

),

	'modules'=>array(
		// uncomment the following to enable the Gii tool
		/*
		'gii'=>array(
			'class'=>'system.gii.GiiModule',
		 	// If removed, Gii defaults to localhost only. Edit carefully to taste.
			'ipFilters'=>array('127.0.0.1','::1'),
		),
		*/
	),

	// application components
	'components'=>array(
		// enables theme based JQueryUI's
        'widgetFactory' => array(
                'widgets' => array(
	                'CJuiTabs' => array(
	                        'themeUrl' => '/agora_front/css',
	                        'theme' => 'start',
	                ),
              	  ),
            ),
		'user'=>array(
			// enable cookie-based authentication
			//'allowAutoLogin'=>true,
	       	'class'=>'application.components.WebUser',
	       	//'loginUrl'=>array('site/login'),
		),
		'excel'=>array(
                 'class'=>'application.extensions.PHPExcel4Yii.PHPExcel',
        ),
		
		//this component is used so as to have local file auth manager
		'authManager'=>array(
         	'class'=>'CPhpAuthManager',
		   // 'defaultRoles'=>array('admin','authenticated'),
		
        ),
        //enable language file . My language = el
		'coreMessages'=>array(
            'basePath'=>'protected/messages',
        ),   

		//http://www.yiiframework.com/extension/contentcompactor#hh3
		'contentCompactor' => array(
	  	 'class' => 'ext.contentCompactor.ContentCompactor',
		    'options' => array(
			 	'compress_css' => true, // Compress CSS
			    'strip_comments' => true, // Remove comments
			    'keep_conditional_comments' => true, // Remove conditional comments
			    'compress_horizontal' => true, // Compress horizontally
			    'compress_vertical' => true, // Compress vertically
			    'compress_scripts' => false, // Compress inline scripts using basic algorithm
			    'line_break' => PHP_EOL, // The type of rowbreak you use in your document
			    'preserved_tags' => array('textarea', 'pre', 'script', 'style', 'code'),
			    'preserved_boundry' => '@@PRESERVEDTAG@@',
			   'conditional_boundries' => array('@@IECOND-OPEN@@', '@@IECOND-CLOSE@@'),
			    'script_compression_callback' => false,
			    'script_compression_callback_args' => array(),
			),
		),
		'session' => array(
            'cookieParams' => array('path' => '/',),
        ),
		// uncomment the following to enable URLs in path-format
		/*
		'urlManager'=>array(
			'urlFormat'=>'path',
			'rules'=>array(
				'<controller:\w+>/<id:\d+>'=>'<controller>/view',
				'<controller:\w+>/<action:\w+>/<id:\d+>'=>'<controller>/<action>',
				'<controller:\w+>/<action:\w+>'=>'<controller>/<action>',
			),
		),
		*/
		'errorHandler'=>array(
			// use 'site/error' action to display errors
            'errorAction'=>'site/error',
        ),
        'log'=>array(
            'class'=>'CLogRouter',
            'routes'=>array(
        		'stdlog' => array(
			            'class'=>'CFileLogRoute',
			            'logFile'=>'test.log',
                	),
                array(
                    'class'=>'CFileLogRoute',
                    'levels'=>'error, warning',
                    'filter'=>'CLogFilter',
                	
                ),
                array(
                    'class'=>'CEmailLogRoute',
                    'levels'=>'error, warning',
                    'emails'=>'admin@example.com',
                ),
             /*   array(
					'class'=>'CWebLogRoute',
					),*/
			),
			)
   
	/*	'log'=>array(
			'class'=>'CLogRouter',
			'routes'=>array(
				array(
					'class'=>'CFileLogRoute',
					'levels'=>'error, warning',
				),
				// uncomment the following to show log messages on web pages
				
				array(
					'class'=>'CWebLogRoute',
				),
				
			),
		),
        
        'log'=>array(
		  'class'=>'CLogRouter',
		  'routes'=>array(
			    array(
			    'class'=>'CWebLogRoute',
			        //
			        // I include *trace* for the 
			        // sake of the example, you can include
			        // more levels separated by commas
			    'levels'=>'trace',
			        //
			        // I include *vardump* but you
			        // can include more separated by commas
			    'categories'=>'vardump',
			        //
			        // This is self-explanatory right?
			    'showInFireBug'=>true
				),
			),
		),*/
		)
 

));
