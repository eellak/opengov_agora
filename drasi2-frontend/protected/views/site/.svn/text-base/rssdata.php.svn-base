<?php 
Yii::import('ext.feed.*');

	if($version=='rss1'){
		$feed = new EFeed(EFeed::RSS1);
			if($type=='procurement')
				$feed->title= Yii::t('yii','rsstitle') ."-". Yii::t('procurement','Procurements');
			if($type=='rfp')
				$feed->title= Yii::t('yii','rsstitle') ."-". Yii::t('rfp','RFP');
			if($type=='contract')
				$feed->title= Yii::t('yii','rsstitle') ."-". Yii::t('contract','Contracts');
			if($type=='payments')
				$feed->title= Yii::t('yii','rsstitle') ."-". Yii::t('payments','Payments');
			$feed->link = $_SERVER['HTTP_REFERER'];
			$feed->description = Yii::t('yii','rssdescription');
	   	if(is_array($data) && isset($data[0]['title'])){
		for($i=0;$i<20;$i++){
				$item = $feed->createNewItem();
				$item->title = $data[$i]['title'];
				if(isset($org) && $org!=''){
					if($type=='procurement')
						$item->link = $this->createAbsoluteUrl('/organizations/viewproc',array('documentUrl'=>$data[$i]['documentUrl'],'org'=>$org));
					if($type=='rfp')
						$item->link = $this->createAbsoluteUrl('/organizations/viewrfp',array('uniqueDocumentCode'=>$data[$i]['uniqueDocumentCode'],'org'=>$org));
					if($type=='contract')
						$item->link = $this->createAbsoluteUrl('/organizations/viewcontract',array('uniqueDocumentCode'=>$data[$i]['uniqueDocumentCode'],'org'=>$org));
					if($type=='payments')
						$item->link = $this->createAbsoluteUrl('/organizations/viewpayment',array('documentUrl'=>$data[$i]['documentUrl'],'org'=>$org));
					
				}else{
					if($type=='procurement')
						$item->link = $this->createAbsoluteUrl('procurement/viewproc',array('documentUrl'=>$data[$i]['documentUrl']));
					if($type=='rfp')
						$item->link = $this->createAbsoluteUrl('rfp/viewrfp',array('uniqueDocumentCode'=>$data[$i]['uniqueDocumentCode']));
					if($type=='contract')
						$item->link = $this->createAbsoluteUrl('contract/viewcontract',array('uniqueDocumentCode'=>$data[$i]['uniqueDocumentCode']));
					if($type=='payments')
						$item->link = $this->createAbsoluteUrl('payments/viewpayment',array('documentUrl'=>$data[$i]['documentUrl']));
					
						
				}
				//echo $data[$i]['dateSigned']."<br/>";
				$explodeDate = explode('/',$data[$i]['submissionTime']);
				$date = date("D, d M Y H:i:s O", mktime(0, 0, 0, $explodeDate[1], $explodeDate[0], $explodeDate[2])); 
				//echo $date ."<br/";
				$item->date  = $date;
				if($type=='procurement'){
					$item->description = "<strong>".Yii::t('procurement','dateSigned')."</strong>: ". $data[$i]['dateSigned'] ."<br/>"
		        					.Yii::t('yii','RssDescProc')." ". $data[$i]['OrganisationName']."<br/>"
		        					 .Yii::t('yii','RssDescADA')." ". $data[$i]['documentUrl'];
					$item->addTag('dc:subject', Yii::t('procurement','Procurements'));
				}else if($type=='rfp'){
					$item->description = "<strong>".Yii::t('procurement','dateSigned')."</strong>: ". $data[$i]['dateSigned'] ."<br/>"
		        					.Yii::t('yii','RssDescRfp')." ". $data[$i]['OrganisationName']."<br/>"
		        					 .Yii::t('yii','RssDescADA')." ". $data[$i]['uniqueDocumentCode'];
					$item->addTag('dc:subject', Yii::t('rfp','RFP'));
				}else if($type=='contract'){
					$item->description = "<strong>".Yii::t('procurement','dateSigned')."</strong>: ". $data[$i]['dateSigned'] ."<br/>"
		        					.Yii::t('yii','RssDescContract')." ". $data[$i]['OrganisationName']."<br/>"
		        					 .Yii::t('yii','RssDescADA')." ". $data[$i]['uniqueDocumentCode'];
					$item->addTag('dc:subject', Yii::t('contract','Contracts'));
				}else if($type=='payments'){
					$item->description = "<strong>".Yii::t('procurement','dateSigned')."</strong>: ". $data[$i]['dateSigned'] ."<br/>"
		        					.Yii::t('yii','RssDescPayment')." ". $data[$i]['OrganisationName']."<br/>"
		        					 .Yii::t('yii','RssDescADA')." ". $data[$i]['uniqueDocumentCode'];
					$item->addTag('dc:subject', Yii::t('payments','Payment'));
				}
				//end type//end type
		    	$feed->addItem($item);
			}//end for
			$feed->generateFeed();
	   	}else echo "No data";
			Yii::app()->end();
	}else if($version=='rss2'){
		$feed = new EFeed();
 
		if($type=='procurement')
			$feed->title= Yii::t('yii','rsstitle') ."-". Yii::t('procurement','Procurements');
		if($type=='rfp')
				$feed->title= Yii::t('yii','rsstitle') ."-". Yii::t('rfp','RFP');
		if($type=='contract')
				$feed->title= Yii::t('yii','rsstitle') ."-". Yii::t('contract','Contracts');
		if($type=='payments')
				$feed->title= Yii::t('yii','rsstitle') ."-". Yii::t('payments','Payments');
			$feed->link = $_SERVER['HTTP_REFERER'];
			$feed->description = Yii::t('yii','rssdescription');
			$feed->addChannelTag('language', 'el-GR');
			$feed->addChannelTag('pubDate', date(DATE_RSS, time()));
 
			if(is_array($data) && isset($data[0]['title'])){
				for($i=0;$i<20;$i++){
					$item = $feed->createNewItem();
					$item->title = $data[$i]['title'];
					if(isset($org) && $org!=''){
						if($type=='procurement')
							$item->link = $this->createAbsoluteUrl('/organizations/viewproc',array('documentUrl'=>$data[$i]['documentUrl'],'org'=>$org));
						if($type=='rfp')
							$item->link = $this->createAbsoluteUrl('/organizations/viewrfp',array('uniqueDocumentCode'=>$data[$i]['uniqueDocumentCode'],'org'=>$org));
						if($type=='contract')
							$item->link = $this->createAbsoluteUrl('/organizations/viewcontract',array('uniqueDocumentCode'=>$data[$i]['uniqueDocumentCode'],'org'=>$org));
						if($type=='payments')
							$item->link = $this->createAbsoluteUrl('/organizations/viewpayment',array('documentUrl'=>$data[$i]['documentUrl'],'org'=>$org));
					
					}else{
						if($type=='procurement')
							$item->link = $this->createAbsoluteUrl('procurement/viewproc',array('documentUrl'=>$data[$i]['documentUrl']));
						if($type=='rfp')
							$item->link = $this->createAbsoluteUrl('rfp/viewrfp',array('uniqueDocumentCode'=>$data[$i]['uniqueDocumentCode']));
						if($type=='contract')
							$item->link = $this->createAbsoluteUrl('contract/viewcontract',array('uniqueDocumentCode'=>$data[$i]['uniqueDocumentCode']));
						if($type=='payments')
							$item->link = $this->createAbsoluteUrl('payments/viewpayment',array('documentUrl'=>$data[$i]['documentUrl']));
					
					}
					$explodeDate = explode('/',$data[$i]['dateSigned']);
					$date = date("D, d M Y H:i:s O", mktime(0, 0, 0, $explodeDate[1], $explodeDate[0], $explodeDate[2])); 
					$item->date  = $date;
			
					if($type=='procurement'){
						$item->description = "<strong>".Yii::t('procurement','dateSigned')."</strong>: ". $data[$i]['dateSigned'] ."<br/>"
			        					.Yii::t('yii','RssDescProc')." ". $data[$i]['OrganisationName']."<br/>"
			        					 .Yii::t('yii','RssDescADA')." ". $data[$i]['documentUrl'];
					}else if($type=='rfp'){
					$item->description = "<strong>".Yii::t('procurement','dateSigned')."</strong>: ". $data[$i]['dateSigned'] ."<br/>"
		        					.Yii::t('yii','RssDescRfp')." ". $data[$i]['OrganisationName']."<br/>"
		        					 .Yii::t('yii','RssDescADA')." ". $data[$i]['uniqueDocumentCode'];
					}else if($type=='contract'){
					$item->description = "<strong>".Yii::t('procurement','dateSigned')."</strong>: ". $data[$i]['dateSigned'] ."<br/>"
		        					.Yii::t('yii','RssDescContract')." ". $data[$i]['OrganisationName']."<br/>"
		        					 .Yii::t('yii','RssDescADA')." ". $data[$i]['uniqueDocumentCode'];
					}else if($type=='payments'){
					$item->description = "<strong>".Yii::t('procurement','dateSigned')."</strong>: ". $data[$i]['dateSigned'] ."<br/>"
		        					.Yii::t('yii','RssDescPayment')." ". $data[$i]['OrganisationName']."<br/>"
		        					 .Yii::t('yii','RssDescADA')." ". $data[$i]['uniqueDocumentCode'];
				}//end type
			    	$feed->addItem($item);
				}//end for
			$feed->generateFeed();
	   	}else echo "No data";			 
			
			Yii::app()->end();
	}

?>
