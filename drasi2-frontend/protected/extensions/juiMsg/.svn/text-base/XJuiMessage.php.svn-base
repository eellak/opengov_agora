<?php
/**
 * XJuiMessage class file.
 *
 * @author Stefan Volkmar <volkmar_yii@email.de>
 * @license BSD
 */

/**
 * This widget generate info- and error messages by using the style definitions of the CSS-framework
 * which is a part of jQuery UI
 *
 * To use this widget, you may insert the following code in a view:
 * <pre>
 * <?php
 * $this->beginWidget('ext.juiMsg.XJuiMessage', array(
 *        "kind"=>"alert",
 *    ));
 *    echo 'Sample ui-state-error style.';
 *
 * $this->endWidget(); ?>
 * </pre>
 *
 * @author Stefan Volkmar <volkmar_yii@email.de>
 */

Yii::import('zii.widgets.jui.CJuiWidget');

Yii::setPathOfAlias('XJuiMessage',dirname(__FILE__));

class XJuiMessage extends CJuiWidget
{	
	/**
	 * @var string the kind of the message ('alert' or 'info')
	 */
	public $kind='info';
	/**
	 * @var string the caption for the message
	 */

    public $caption=false;

	/**
	 * Renders the open tags of the message.
	 * This method also registers the necessary javascript code.
	 */
	public function init()
	{
		parent::init();
        echo '<div class="ui-widget">'."\n";

        $kind = strtolower($this->kind);
        if ($kind=="info"){
            echo '<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: .5em .7em;">'."\n";
            echo '<p><span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>'."\n";
            if (!is_null($this->caption)){
                if ($this->caption==false) {
                    echo "<strong>".Yii::t("XJuiMessage.main",'Information: ')."</strong>";
                } else {
                    echo "<strong>".$this->caption."</strong>";
                }
            }
        } elseif ($kind=="alert") {
            echo '<div class="ui-state-error ui-corner-all" style="padding: .5em .7em;">'."\n";
            echo '<p><span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span> '."\n";            
            if (!is_null($this->caption)){
                if ($this->caption==false) {
                    echo "<strong>".Yii::t("XJuiMessage.main",'Alert: ')."</strong>";
                } else {
                    echo "<strong>".$this->caption."</strong>";
                }
            }
        } else {
            throw new CException(Yii::t("XJuiMessage.main",
                    'Invalid value. Property "kind" can only set to "alert" or "info".'));
        }
	}

	/**
	 * Renders the close tags of the message.
	 */
	public function run(){
        echo '</p></div></div>'."\n";
	}


}
