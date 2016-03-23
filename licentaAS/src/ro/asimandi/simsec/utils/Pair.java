package ro.asimandi.simsec.utils;

public class Pair<T,U> {
	private T fst;
	private U snd;
	
	public T getFst() {
		return fst;
	}

	public void setFst(T fst) {
		this.fst = fst;
	}

	public U getSnd() {
		return snd;
	}

	public void setSnd(U snd) {
		this.snd = snd;
	}


	
	Pair(T t, U u){
		fst = t;
		snd = u;
	}

}
