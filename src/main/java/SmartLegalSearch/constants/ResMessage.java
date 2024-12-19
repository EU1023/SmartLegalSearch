package SmartLegalSearch.constants;

public enum ResMessage {

	// ±`³W¸o¦D
	MURDER("±þ¤H¸o"), //
	INJURY("¶Ë®`¸o"), //
	ROBBERY("±jµs¸o"), //
	THEFT("ÅÑµs¸o"), //
	FRAUD("¶B´Û¸o"), //
	EXTORTION("·m¹Ü¸o"), //
	SEXUAL_ASSAULT("©Ê«I®`¸o"), //
	PUBLIC_DANGER("¤½¦@¦MÀI¸o"), //
	CORRUPTION("³g¦Ã¸o"), //
	FORGERY_DOCUMENT("°°³y¤å®Ñ¸o"), //
	FORGERY_CURRENCY("°°³y³f¹ô¸o"), //
	PERJURY("°°ÃÒ¸o"), //
	BRIBERY_RECEIVING("¨ü¸ì¸o"), //
	ARSON("Áa¤õ¸o"), //
	THEFT_PUBLIC_ASSETS("ÅÑ¨ú¤½¦@°]ª«¸o"), //
	DRUG_CRIMES("¬r«~¥Ç¸o"), //
	FORGERY_COMPANY_SEAL("°°³y¤½¥q¦L³¹¸o"), //
	BRIBERY_GIVING("¦æ¸ì¸o"), //
	STEALING("°½ÅÑ¸o"), //
	BRIBERY("¸ì¸ï¸o"), //
	FAMILY_OFFENSE("§«®`®a®x¸o"), //
	INSULT("¤½µM«V°d¸o"), //
	EMBEZZLEMENT("«I¥e¸o"), //
	HIT_AND_RUN("»F¨Æ°k¶h¸o"),
    NEGLIGENCE_CAUSING_DEATH("¹L¥¢­P¦º¸o"),
    NEGLIGENCE_CAUSING_INJURY("¹L¥¢¶Ë®`¸o"),

	// ¯S®íªk«ßªº¸o¦D
	REBELLION("¤º¶Ã¸o"), //
	TREASON("¥~±w¸o"), //
	NEGLIGENCE("ÂpÂ¾¸o"), //
	OBSTRUCTION_OF_OFFICE("§«®`¤½°È¸o"), //
	CONTEMPT_OF_PARLIAMENT("ÂÆµø°ê·|¸o"), //
	VOTING_OBSTRUCTION("§«®`§ë²¼¸o"), //
	ESCAPE("²æ°k¸o"), //
	HARBORING_CRIMINALS("ÂÃ°Î¤H¥Ç¤Î´ó·ÀÃÒ¾Ú¸o"), //
	SACRILEGE_AND_VANDALISM("Á¶ÂpªÁ¨å¤Î«I®`¼X¹Ó«ÍÅé¸o"), //
	OBSTRUCTION_OF_AGRICULTURE("§«®`¹A¤u°Ó¸o"), //
	OPIUM_CRIMES("¾~¤ù¸o"), //
	GAMBLING("½ä³Õ¸o"), //
	COMPUTER_CRIMES("§«®`¹q¸£¨Ï¥Î¸o");

	private final String crimeName;
	

	private ResMessage(String crimeName) {
		this.crimeName = crimeName;
	}

	public String getCrimeName() {
		return crimeName;
	}

	@Override
	public String toString() {
		return this.crimeName;
	}

}
