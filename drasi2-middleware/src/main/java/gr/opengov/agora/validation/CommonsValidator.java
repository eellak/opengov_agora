package gr.opengov.agora.validation;

public class CommonsValidator implements ICommonsValidator{
	@Override
	public boolean isOkAfm(String afmToCheck){
		if ((afmToCheck == null) || (afmToCheck.trim().length() == 0) || (afmToCheck.trim().length() > 9) || (afmToCheck.trim().equals("000000000")))
			return false;
        char[] afm;
        double count=0;
        double digit,finalNum=0;
        String temp;
        afm=afmToCheck.toCharArray();

        for(int i=afm.length;i>=1;i--){
            if(count!=0){
            temp=String.valueOf(afm[i-1]);
            digit=Integer.parseInt(temp);
           finalNum=finalNum+digit*Math.pow(2,count);
            }
           count++;
        }

        temp=String.valueOf(afm[afm.length-1]);
        digit=Integer.parseInt(temp);

        if(((finalNum%11)%10)==digit){
           return true;
        }
        else return false;
    }
}
