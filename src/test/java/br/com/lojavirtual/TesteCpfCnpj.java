package br.com.lojavirtual;



import br.com.lojavirtual.util.ValidaCnpj;
import br.com.lojavirtual.util.ValidaCpf;

class TesteCpfCnpj {

	
	public static void main(String[] args) {
		boolean isCnpj = ValidaCnpj.isCNPJ("66.347.536/0001-96");
		System.out.println("CNPJ valido: " + isCnpj);
		
		boolean isCpf = ValidaCpf.isCPF("255.326.610-30");
		System.out.println("CPF valido: " + isCpf);
	}

}
