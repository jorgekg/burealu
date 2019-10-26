package br.com.bureau.earnings.models.enuns;

public enum Role {
	
	TRACKING(1, "tracking"),
	EARNINGS(2, "Earnings"),
	DETAILS(3, "Details");
	
	private int cod;
	private String descricao;
	
	private Role(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDresciption() {
		return descricao;
	}
	
	public static Role toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (Role x : Role.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
	}

}
