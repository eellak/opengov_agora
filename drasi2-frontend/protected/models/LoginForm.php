<?php

/**
 * LoginForm class.
 * LoginForm is the data structure for keeping
 * user login form data. It is used by the 'login' action of 'SiteController'.
 * It uses recaptcha. http://www.hollowdevelopers.com/2011/03/27/yii-framework-recaptcha-with-active-form/
 * 
 */
class LoginForm extends CFormModel
{
	/**
	 * the users username 
	 * @var string
	 */
	public $username;
	/**
	 * the users password 
	 * @var string
	 */
	public $password;
	public $role;
	/**
	 * the users selection of remember choice
	 * @var boolean
	 */
	public $rememberMe=false;
	/**
	 * the validacion string of captcha
	 * @var string
	 */
	public $validacion;

	private $_identity;

	/**
	 * Declares the validation rules.
	 * The rules state that username and password are required,
	 * and password needs to be authenticated.
	 */
	public function rules()
	{
		return array(
			// username and password are required
			//array('username, password,validacion', 'required'),
			array('username, password', 'required'),
			
			// password needs to be authenticated
			array('password', 'authenticate'),
			//array('validacion', 
             //  'application.extensions.recaptcha.EReCaptchaValidator', 
              // 'privateKey'=>Yii::app()->params['captchaPrivateKey']),
            array('validacion', 'captcha', 'allowEmpty'=>!extension_loaded('gd')),
			
			
		);
	}

	/**
	 * Declares attribute labels.
	 */
	public function attributeLabels()
	{
		return array(
			'username'=>Yii::t('form','username'),
			'password'=>Yii::t('form','password'),
		    'validacion'=>Yii::t('form', 'validacion'),
		);
	}

	/**
	 * Authenticates the password.
	 * This is the 'authenticate' validator as declared in rules().
	 */
	public function authenticate($attribute,$params)
	{
		if(!$this->hasErrors())
		{
			$this->_identity=new UserIdentity($this->username,$this->password);
			
			if(!$this->_identity->authenticate()){
				$errors = new ApiErrorHandling('contract');	
				$this->addError($errors->NameError($this->_identity->errorCode),$errors->DescriptionError($this->_identity->errorCode));
			}
		}
	}

	/**
	 * Logs in the user using the given username and password in the model.
	 * @return boolean whether login is successful
	 */
	public function login()
	{
		if($this->_identity===null)
		{
			$this->_identity=new UserIdentity($this->username,$this->password);
			$this->_identity->authenticate();
		}
		if($this->_identity->errorCode===UserIdentity::ERROR_NONE)
		{
			$duration=$this->rememberMe ? 3600*24*30 : 0; // 30 days
			Yii::app()->user->login($this->_identity,$duration);
			return true;
		}
		else
			return false;
	}
}
