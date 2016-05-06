<?php
/**
 * This class supports the Contact procedure.
 * It is the main model Class. 
 * ContactForm is the data structure for keeping
 * contact form data. It is used by the 'contact' action of 'SiteController'.
 * @author themiszamani
 * @version 1.0
 * @package agora 
 */

class ContactForm extends CFormModel
{
	/**
     * The name of the user trying to contact us
     * @var string
     */
	public $name;
	/**
     * The email of the user trying to contact us
     * @var string
     */
	public $email;
	/**
     * The subject of the issue
     * @var string
     */
	public $subject;
	/**
     * The body of the issue
     * @var string
     */
	public $body;
	/**
     * The verifycode of captcha
     * @var string
     */
	public $verifyCode;

	/**
	 * The main function that validates the attributes - properties of a contact issue. 
 	 * The purpose is to have all the information gathered in one place instead of scattered.
 	 * See the following link on how to add a new rule to payment. 
	 * The validation rules are as follows:
	 * @link http://www.yiiframework.com/wiki/56/reference-model-rules-validation/#hh4
	 * @return array with the errors
	 */
	public function rules()
	{
		return array(
			// name, email, subject and body are required
			array('name, email, subject, body', 'required'),
			// email has to be a valid email address
			array('email', 'email'),
			// verifyCode needs to be entered correctly
			array('verifyCode', 'captcha', 'allowEmpty'=>!CCaptcha::checkRequirements()),
		);
	}

	/**
	 * Declares customized attribute labels.
	 * If not declared here, an attribute would have a label that is
	 * the same as its name with the first letter in upper case.
	 * @return array customized attribute labels (name=>label)
	 */
	public function attributeLabels()
	{
		return array(
			'verifyCode'=>'Verification Code',
		);
	}
}