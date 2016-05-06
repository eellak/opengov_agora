<?php
/*http://bassistance.de/jquery-plugins/jquery-plugin-autocomplete/*/


$q = strtolower($_GET["term"]);
if (!$q) return;

$Xml2Array = array();
	 	//$output = $this->SetCurlData($type);
	 	
		$filename = "cpv_codes.xml";
	 	//echo $filename ."<br/>";
		$handle = fopen($filename, "r");
		$output = fread($handle, filesize($filename));
		//print_r($output);
	 	$xml = new SimpleXMLElement($output);
	 	$i=0;
		 	foreach ($xml->children() as $child) {
	  		 
	  		 	$Xml2Array["[".$child->attributes()."]-".$child[0]->label.""] = "".$child->attributes()."" ;
	  			$i++; 
		 	}

echo"[";
foreach ($Xml2Array as $key=>$value) {
	if (strpos(strtolower($key), $q) !== false) {
		echo '{"id":"'.$value .'","value":"'.$key.'","info":"'.$value.'"},';
	}
}
echo"]";

//echo'[ { "id": "Botaurus stellaris", "label": "Great Bittern1", "value": "Great Bittern" }, { "id": "Podiceps nigricollis", "label": "Black-necked Grebe", "value": "Black-necked Grebe" }, { "id": "Nycticorax nycticorax", "label": "Black-crowned Night Heron", "value": "Black-crowned Night Heron" }, { "id": "Netta rufina", "label": "Red-crested Pochard", "value": "Red-crested Pochard" }, { "id": "Circus cyaneus", "label": "Hen Harrier", "value": "Hen Harrier" }, { "id": "Circus pygargus", "label": "Montagu`s Harrier", "value": "Montagu`s Harrier" }, { "id": "Tetrao tetrix", "label": "Black Grouse", "value": "Black Grouse" }, { "id": "Perdix perdix", "label": "Grey Partridge", "value": "Grey Partridge" }, { "id": "Porzana porzana", "label": "Spotted Crake", "value": "Spotted Crake" }, { "id": "Crex crex", "label": "Corncrake", "value": "Corncrake" }, { "id": "Grus grus", "label": "Common Crane", "value": "Common Crane" }, { "id": "Recurvirostra avosetta", "label": "Avocet", "value": "Avocet" } ]';
?>