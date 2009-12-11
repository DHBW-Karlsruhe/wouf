package org.bh.calculation.sebi;

public class GermanTax implements Tax {
	
	/** Business Tax (Gewerbesteuer) */
	private Calculable sg;
	
	/** Corporate Income Tax incl. solidarity tax (Körperschaftssteuer incl. Soli) */ 
	private Calculable sks;
	
	/** Aggregated Taxes */
	private Calculable aggregatedTaxG;
	
	public GermanTax(Calculable sg, Calculable sks){
		this.sg = sg;
		this.sks = sks;
		calcGermanTax(sg, sks);
	}
	
	private void calcGermanTax(Calculable sg, Calculable sks){
		//this.gesamt = (0.5 * sg * (1 - sks) + sks); 
		this.aggregatedTaxG = (Calculable) sg.mul(new DoubleValue(0.5)).mul(sks.mul(new DoubleValue(-1)).add(new DoubleValue(1)));
		this.aggregatedTaxG = (Calculable) aggregatedTaxG.add(sks);
	}

	public Calculable getSg() {
		return sg;
	}

	public Calculable getSks() {
		return sks;
	}

	@Override
	public Calculable getAggregated() {
		return aggregatedTaxG;
	}
	
	@Override
	public GermanTax clone() {
		return new GermanTax(sg, sks);
	}
}
