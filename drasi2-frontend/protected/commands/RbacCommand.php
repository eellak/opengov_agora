<?php
/**
 * 
 * This is the frontend User - Action control system
 * There are 2 types of users a) authenticated, b) admin
 * First i create the Operactions of each model
 * Then roles are assigned to types of users. 
 * @author themiszamani
 *
 */
class RbacCommand extends CConsoleCommand
{
	
        public function run()
        {
                $auth=Yii::app()->authManager;
                $auth->createOperation('contactperson','contact a person');
                $auth->createOperation('indexpage','present index page');
                $auth->createOperation('viewpage','view pages');
                $auth->createOperation('userprofile','view users profile');
                
                $auth->createOperation('indexProc','View the list of your procurements');
                $auth->createOperation('viewProc','View the details of a procurement');
                $auth->createOperation('createProc','Create a new procurement');
                $auth->createOperation('updateProc','Update an existing procurement');
                $auth->createOperation('approveRequest','Approve the user request');
                $auth->createOperation('deleteProc','Delete a procurement');
                $auth->createOperation('downloadProc','Download the file of a procurement');
                $auth->createOperation('procApprovals','List of procurement type');
                $auth->createOperation('cancelproc','Cancels a procurement');
                
                $auth->createOperation('index','View the list of your Rfp');
                $auth->createOperation('createrfp','Create a new Rfp');
                $auth->createOperation('updaterfp','Update an existing Rfp');
                $auth->createOperation('deleterfp','Delete a Rfp');
                $auth->createOperation('cancelrfp','Cancels an RFP');
                $auth->createOperation('rfpdownload','Download the file of a Rfp');
                
                $auth->createOperation('indexContracts','View the list of your contracts');
                $auth->createOperation('viewContract','View the details of a contract');
                $auth->createOperation('viewContractPayments','View contract payments');
                $auth->createOperation('createContract','Create a new contract');
                $auth->createOperation('createContractByProc','Create a new contract by using Procurement Ids');
                $auth->createOperation('updateContract','Update an existing contract');
                $auth->createOperation('extendContract','Extend a contract');
                $auth->createOperation('deleteContract','Delete a contract');
                $auth->createOperation('cancelcontract','Cancels a contract');
                $auth->createOperation('downloadContract','Download the file of a contract');
                
                $auth->createOperation('indexpayments','View the list of your payments');
                $auth->createOperation('viewPayment','View the details of a payment');
                $auth->createOperation('createPayment','Create a new payment');
                $auth->createOperation('CreatePaymentByContract','Create a new payment through contract');
                $auth->createOperation('CreatePaymentByProc','Create a new payment based on procurements');
                $auth->createOperation('createEmptyPayment','Create Payment From Scratch');
                $auth->createOperation('updatePayment','Update an existing Payment');
                $auth->createOperation('deletePayment','Delete a Payment');
                $auth->createOperation('cancelpayment','Cancels a contract');
                $auth->createOperation('downloadPayment','Download the file of a Payment');
                
                $role=$auth->createRole('superadmin');
                $role->addChild('contactperson');
                $role->addChild('userprofile');
                $role->addChild('indexProc');
                $role->addChild('viewProc');
                $role->addChild('updateProc');
                $role->addChild('deleteProc');
                $role->addChild('cancelproc');
                $role->addChild('downloadProc');
                $role->addChild('procApprovals');
                $role->addChild('index');
                $role->addChild('updaterfp');
                $role->addChild('cancelrfp');
                $role->addChild('deleterfp');
                $role->addChild('viewContract');
                $role->addChild('viewContractPayments');
                $role->addChild('updateContract');
                $role->addChild('deleteContract');
                $role->addChild('cancelcontract');
                $role->addChild('downloadContract');
       			$role->addChild('indexpayments');
                $role->addChild('viewPayment');
                $role->addChild('updatePayment');
                $role->addChild('deletePayment');
                $role->addChild('cancelpayment');
                $role->addChild('downloadPayment');
                
                $role=$auth->createRole('admin');
                $role->addChild('contactperson');
                $role->addChild('userprofile');
                $role->addChild('indexProc');
                $role->addChild('viewProc');
                $role->addChild('createProc');
                $role->addChild('updateProc');
                $role->addChild('approveRequest');
                $role->addChild('deleteProc');
                $role->addChild('downloadProc');
                $role->addChild('cancelproc');
                $role->addChild('procApprovals');
                $role->addChild('index');
                $role->addChild('createrfp');
                $role->addChild('updaterfp');
                $role->addChild('cancelrfp');
                $role->addChild('deleterfp');
                $role->addChild('viewContract');
                $role->addChild('viewContractPayments');
                $role->addChild('createContract');
                $role->addChild('updateContract');
                $role->addChild('extendContract');
                $role->addChild('cancelcontract');
                $role->addChild('deleteContract');
                $role->addChild('downloadContract');
                $role->addChild('createContractByProc');
       			$role->addChild('indexpayments');
                $role->addChild('viewPayment');
                $role->addChild('createPayment');
                $role->addChild('CreatePaymentByContract');
                $role->addChild('createEmptyPayment');
               	$role->addChild('CreatePaymentByProc');
                $role->addChild('updatePayment');
                $role->addChild('cancelpayment');
                $role->addChild('deletePayment');
                $role->addChild('downloadPayment');
                
                $role=$auth->createRole('authenticated');
                $role->addChild('indexpage');
                $role->addChild('userprofile');
                $role->addChild('indexProc');
                $role->addChild('viewProc');
                $role->addChild('createProc');
                $role->addChild('cancelproc');
                $role->addChild('downloadProc');
                $role->addChild('procApprovals');
     			$role->addChild('index');
     			$role->addChild('cancelrfp');
     			$role->addChild('createrfp');
               	$role->addChild('indexContracts');
                $role->addChild('viewContract');
                $role->addChild('createContract');
                $role->addChild('viewContractPayments');
                $role->addChild('extendContract');
                $role->addChild('downloadContract');
       			$role->addChild('createContractByProc');
                $role->addChild('cancelcontract');
                $role->addChild('indexpayments');
                $role->addChild('viewPayment');
                $role->addChild('createPayment');
                $role->addChild('CreatePaymentByContract');
               	$role->addChild('CreatePaymentByProc');
                $role->addChild('createEmptyPayment');
                $role->addChild('cancelpayment');
                $role->addChild('downloadPayment');
                
                //$auth->assign($role, $id);
                $auth->save();
        }
}
?>