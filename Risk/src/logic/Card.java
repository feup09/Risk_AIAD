package logic;

public class Card {
	FIGURE figure;
	TERRITORY territory;
	public enum FIGURE {HORSE,SOLDIER,CANNON,ALL};
	public enum TERRITORY{
		//NORTH AMERICA
    ALASKA,
    ALBERTA,
    CENTRAL_AMERICA,
    EASTERN_UNITED_STATES,
    GREENLAND,
    NORTHWEST_TERRITORY,
    ONTARIO,
    QUEBEC,
    WESTERN_UNITED_STATES,
		//SOUTH AMERICA
	ARGENTINA,
    BRAZIL,
    PERU,
    VENEZUELA,
		//EUROPE
    GREAT_BRITAIN,
    ICELAND,
    NORTHERN_EUROPE,
    SCANDINAVIA,
    SOUTHERN_EUROPE,
    UKRAINE,
    WESTERN_EUROPE,
		//AFRICA
		CONGO,
    EAST_AFRICA,
    EGYPT,
    MADAGASCAR,
    NORTH_AFRICA,
    SOUTH_AFRICA,
		//ASIA
	AFGHANISTAN,
    CHINA,
    INDIA,
    IRKUTSK,
    JAPAN,
    KAMCHATKA,
    MIDDLE_EAST,
    MONGOLIA,
    SIAM,
    SIBERIA,
    URAL,
    YAKUTSK,
		//AUSTRALIA
		EASTERN_AUSTRALIA,
    INDONESIA,
    NEW_GUINEA,
    WESTERN_AUSTRALIA
	}

	public Card(FIGURE figure,TERRITORY territory){

		this.figure=figure;
		this.territory=territory;
		System.out.println("Nova carta: Figura - " + this.figure + " Territorio -" +  this.territory);

	}
}
