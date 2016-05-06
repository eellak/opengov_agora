<?php 

/**
 * http://www.yiiframework.com/forum/index.php?/topic/10214-bored-with-setflash-use-addflash-and-multiflash/
 * This is a class to extend User functions.  It mainly creates and 
 * manipulates flash messages. 
 * @author themiszamani
 *
 */
class WebUser extends CWebUser {
        
        const MF_KEY_PREFIX = 'mf';
        const MF_MAX = 100;
        
        /**
         * Add flash to the stack.
         * @param string $msg
         * @param string $class 
         */
        public function addFlash($msg='', $class='info') {
                $key = $this->getNexMultiFlashKey();
                if ($key===false) 
                        Yii::trace("Stack overflow in addFlash", 'application.webuser.addFlash()');
                else
                        $this->setFlash($key, array($msg, $class));
        }
        
        /**
         * Returns next flash key for addFlash function.
         * @return mixed string if ok or bool false if there was stack overflow.
         */
        protected function getNexMultiFlashKey() {
                $counters=$this->getState(self::FLASH_COUNTERS,array());
                if (empty($counters)) return self::MF_KEY_PREFIX."1";
                for ($i=1;$i<=self::MF_MAX; $i++) {
                        $key = self::MF_KEY_PREFIX . (string)$i;
                        if (array_key_exists($key, $counters)) continue;
                        return $key;
                }
                return false;
        }
        
        /**
         * Gets all flashes and shows them to the user.
         * Every flash is div with css class 'flash' and another 'flash_xxx' where xxx is the $class
         * parameter set in addFlash function.
         * Examples:
         * Yii::app()->user->addFlash('My text, something important!', 'warning');
         * Yii::app()->multiFlash();
         * Output is <div class="flash flash_warning">My text, something important!<div>
         */
        public function multiFlash() {
                for ($i=1;$i<=self::MF_MAX; $i++) {
                        $key = self::MF_KEY_PREFIX . (string)$i;
                        if (!$this->hasFlash($key)) continue;
                        list($msg, $class) = $this->getFlash($key);
                        Yii::trace("Echoing multiFlash: $key", 'application.webuser.multiFlash()');
                        echo "<div class=\"flash flash_$class\">$msg</div>\n";
                }
        }
        
        

        
}
?>