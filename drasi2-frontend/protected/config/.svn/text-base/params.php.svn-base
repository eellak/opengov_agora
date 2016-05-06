<?php
// application-level parameters that can be accessed
// using Yii::app()->params['paramName']
//https://www.google.com/recaptcha/admin/site?siteid=314826627
$urlMiddle = 'http://83.212.121.173:8080/agora/';

return array(

	'params'=>array(
		// this is used in contact page
		'adminEmail'=>'webmaster@example.com',
		'captchaPublicKey'=>'6LeD38MSAAAAAMGVutNtiJ3CrHIsOiSKrY--BBxa',
		'captchaPrivateKey'=>'6LeD38MSAAAAALJrsRiK2xch2dj7NFwbHuwf4OoF',
		'agora'=>array('auth'=>'http://agora.opengov.gr/schema/auth-0.1',
					   'common'=>'http://agora.opengov.gr/schema/common-0.1',		
					   'authenticate'=>$urlMiddle.'authenticate',
					   'procurement'=>'http://agora.opengov.gr/schema/procurement-request-0.1',
					   'rfp'=>'http://agora.opengov.gr/schema/notice-0.1',
					   'contract'=>'http://agora.opengov.gr/schema/contract-0.1',
					   'payment'=>'http://agora.opengov.gr/schema/payment-0.1',
					   'xmlSchema'=>'http://www.w3.org/2001/XMLSchema-instance',
					   'ConnectionTimeout'=>10,
					   'opendata'=>'http://opendata.diavgeia.gov.gr/api/',
						'rsslimit'=>20,
					   
				 ),
		'ode'=>array('list'=>$urlMiddle.'taxonomy/oda/'),
				 
		'procurement'=>array('list'=>$urlMiddle.'procurementRequests/',
				 	'download'=>$urlMiddle.'procurementRequests/documents/',
				 	'approve'=>$urlMiddle.'procurementRequest/approval/'
		),
		'rfp'=>array('list'=>$urlMiddle.'notices/',
					'short'=>$urlMiddle.'notices/?output=short',
				 	'item'=>$urlMiddle.'notices/',	 
				 	'download'=>$urlMiddle.'notices/documents/',
		),
		'contract'=>array('short'=>$urlMiddle.'contracts/?output=short',
				 	'item'=>$urlMiddle.'contracts/',	 
				 	'download'=>$urlMiddle.'contracts/documents/',
		),
		'payments'=>array(
					 'item'=>$urlMiddle.'payments/',
					 'list'=>$urlMiddle.'payments/',
				 	 'download'=>$urlMiddle.'payments/documents/'
		),
		'parameters'=>array(
					'actionPermitted'=>86400,
		),
		'taxonomy'=>array(
					'cpv_codes'=>$urlMiddle.'taxonomy/cpv_codes/',
					'contract_type'=>$urlMiddle.'taxonomy/contract_type',
					'currency'=>$urlMiddle.'taxonomy/currency',
					'country'=>$urlMiddle.'taxonomy/country',
					'commision_criteria'=>$urlMiddle.'taxonomy/commission_criteria',
					'contracting_authority'=>$urlMiddle.'taxonomy/contracting_authority',
					'award_procedure'=>$urlMiddle.'taxonomy/award_procedure',
					'units_of_measure'=>$urlMiddle.'taxonomy/units_of_measure',
					'units1' =>$urlMiddle.'taxonomy/units/',
					'signers1' =>$urlMiddle.'taxonomy/signers/',
					'cancellationType' =>$urlMiddle.'taxonomy/cancellation_type/',
		
		),
		'files'=>array('maxsize'=> ini_get('post_max_size'), 
						  'types'=>'pdf',
		
		)
	)
	
);
?>