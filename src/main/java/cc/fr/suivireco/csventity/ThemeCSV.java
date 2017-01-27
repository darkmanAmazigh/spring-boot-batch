package cc.fr.suivireco.csventity;

/**
 * Declaration of the POJO mapping the themesDelta CSV file
 *
 * @author Rafik BOUGHANI
 * @date 09/01/2017
 */
public class ThemeCSV {

  private String cl_crc;

  private String cl_id;

  private String theme_libl;

  /**
   * @return cl_crc
   */
  public String getCl_crc() {

    return this.cl_crc;
  }

  /**
   * @param cl_crc the cl_crc to set
   */
  public void setCl_crc(String cl_crc) {

    this.cl_crc = cl_crc;
  }

  /**
   * @return cl_id
   */
  public String getCl_id() {

    return this.cl_id;
  }

  /**
   * @param cl_id the cl_id to set
   */
  public void setCl_id(String cl_id) {

    this.cl_id = cl_id;
  }

  /**
   * @return theme_libl
   */
  public String getTheme_libl() {

    return this.theme_libl;
  }

  /**
   * @param theme_libl the theme_libl to set
   */
  public void setTheme_libl(String theme_libl) {

    this.theme_libl = theme_libl;
  }

}
