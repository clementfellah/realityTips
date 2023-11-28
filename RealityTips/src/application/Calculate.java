package application;

public class Calculate {
	
	double bill, tip;
	int nbPeople;
	
	public Calculate(double bill, double tip, int nbPeople)
	{
		this.bill = bill;
		this.tip = tip;
		this.nbPeople = nbPeople;
	}
	
	public double getTipPerPerson(double bill, double tip, int nbPeople)
	{
		double tipPerPerson = 0;		
		tipPerPerson =((bill/100)*tip)/nbPeople;
		
		return tipPerPerson;
	}
	
	public double getTotalPerPerson(double bill, double tip, int nbPeople)
	{
		double totalPerPerson = 0;	
		totalPerPerson = (bill/nbPeople) + getTipPerPerson(bill, tip, nbPeople);
				
		return totalPerPerson;
	}

}
