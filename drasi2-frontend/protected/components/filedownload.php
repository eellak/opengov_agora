<?php
class filedownload{
	
	public function __construct($output,$folder,$file){
		if($output!='excel'){
			$Path = str_replace("index.php", "", Yii::app()->request->scriptFile);
			$filepath = $Path."protected/data/".$folder."/".$file;
			$fh = fopen($filepath, 'wb');
			fwrite($fh, $output);
			fclose($fh);
		}
		$mimeType = 'application/pdf';
 		header('Content-Description: File Transfer');
   		header('Content-Type: ' . $mimeType);
		header('Content-Disposition: attachment; filename='.$file);
		header('Expires: 0');
		header('Cache-Control: must-revalidate, post-check=0, pre-check=0');
		header('Pragma: public');
		header('Content-Length: ' . filesize($filepath));
		readfile($filepath);
	}
}