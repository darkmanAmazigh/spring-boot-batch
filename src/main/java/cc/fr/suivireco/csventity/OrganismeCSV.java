package cc.fr.suivireco.csventity;

/**
 * Declaration of the POJO mapping the OrganismeDelta CSV file
 *
 * @author Rafik BOUGHANI
 * @date 09/01/2017
 */
public class OrganismeCSV {

  private Long or_crc;

  private String or_nom;

  private Long or_id;

  private Long or_dpt;

  private String or_nat;

  private String ortyp;

  private String typ_code;

  /**
   * @return typ_code
   */
  public String getTyp_code() {

    return this.typ_code;
  }

  /**
   * @param typ_code the typ_code to set
   */
  public void setTyp_code(String typ_code) {

    this.typ_code = typ_code;
  }

  /**
   * @return or_dpt
   */
  public Long getOr_dpt() {

    return this.or_dpt;
  }

  /**
   * @param or_dpt the or_dpt to set
   */
  public void setOr_dpt(Long or_dpt) {

    this.or_dpt = or_dpt;
  }

  /**
   * @return or_nat
   */
  public String getOr_nat() {

    return this.or_nat;
  }

  /**
   * @param or_nat the or_nat to set
   */
  public void setOr_nat(String or_nat) {

    this.or_nat = or_nat;
  }

  /**
   * @return ortyp
   */
  public String getOrtyp() {

    return this.ortyp;
  }

  /**
   * @param ortyp the ortyp to set
   */
  public void setOrtyp(String ortyp) {

    this.ortyp = ortyp;
  }

  /**
   * @return or_crc
   */
  public Long getOr_crc() {

    return this.or_crc;
  }

  /**
   * @param or_crc the or_crc to set
   */
  public void setOr_crc(Long or_crc) {

    this.or_crc = or_crc;
  }

  /**
   * @return or_nom
   */
  public String getOr_nom() {

    return this.or_nom;
  }

  /**
   * @param or_nom the or_nom to set
   */
  public void setOr_nom(String or_nom) {

    this.or_nom = or_nom;
  }

  /**
   * @return or_id
   */
  public Long getOr_id() {

    return this.or_id;
  }

  /**
   * @param or_id the or_id to set
   */
  public void setOr_id(Long or_id) {

    this.or_id = or_id;
  }
}
