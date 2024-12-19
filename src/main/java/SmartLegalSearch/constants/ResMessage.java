package SmartLegalSearch.constants;

public enum ResMessage {

	// �`�W�o�D
	MURDER("���H�o"), //
	INJURY("�ˮ`�o"), //
	ROBBERY("�j�s�o"), //
	THEFT("�ѵs�o"), //
	FRAUD("�B�۸o"), //
	EXTORTION("�m�ܸo"), //
	SEXUAL_ASSAULT("�ʫI�`�o"), //
	PUBLIC_DANGER("���@�M�I�o"), //
	CORRUPTION("�g�øo"), //
	FORGERY_DOCUMENT("���y��Ѹo"), //
	FORGERY_CURRENCY("���y�f���o"), //
	PERJURY("���Ҹo"), //
	BRIBERY_RECEIVING("����o"), //
	ARSON("�a���o"), //
	THEFT_PUBLIC_ASSETS("�Ѩ����@�]���o"), //
	DRUG_CRIMES("�r�~�Ǹo"), //
	FORGERY_COMPANY_SEAL("���y���q�L���o"), //
	BRIBERY_GIVING("���o"), //
	STEALING("���Ѹo"), //
	BRIBERY("���o"), //
	FAMILY_OFFENSE("���`�a�x�o"), //
	INSULT("���M�V�d�o"), //
	EMBEZZLEMENT("�I�e�o"), //
	HIT_AND_RUN("�F�ưk�h�o"),
    NEGLIGENCE_CAUSING_DEATH("�L���P���o"),
    NEGLIGENCE_CAUSING_INJURY("�L���ˮ`�o"),

	// �S��k�ߪ��o�D
	REBELLION("���øo"), //
	TREASON("�~�w�o"), //
	NEGLIGENCE("�p¾�o"), //
	OBSTRUCTION_OF_OFFICE("���`���ȸo"), //
	CONTEMPT_OF_PARLIAMENT("�Ƶ���|�o"), //
	VOTING_OBSTRUCTION("���`�벼�o"), //
	ESCAPE("��k�o"), //
	HARBORING_CRIMINALS("�ðΤH�Ǥδ���Ҿڸo"), //
	SACRILEGE_AND_VANDALISM("���p����ΫI�`�X�ӫ���o"), //
	OBSTRUCTION_OF_AGRICULTURE("���`�A�u�Ӹo"), //
	OPIUM_CRIMES("�~���o"), //
	GAMBLING("��ոo"), //
	COMPUTER_CRIMES("���`�q���ϥθo");

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
