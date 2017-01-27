package cc.fr.suivireco.csventity;

/**
 * Declaration of the POJO mapping the ControlDelta CSV file
 *
 * @author Rafik BOUGHANI
 * @date 09/01/2017
 */
public class ControlCSV {

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.CD_CODE == null) ? 0 : this.CD_CODE.hashCode());
    result = prime * result + ((this.CD_LIBL == null) ? 0 : this.CD_LIBL.hashCode());
    result = prime * result + ((this.CL_CRC == null) ? 0 : this.CL_CRC.hashCode());
    result = prime * result + ((this.CL_ID == null) ? 0 : this.CL_ID.hashCode());
    result = prime * result + ((this.CL_LIBC == null) ? 0 : this.CL_LIBC.hashCode());
    result = prime * result + ((this.CL_NUM == null) ? 0 : this.CL_NUM.hashCode());
    result = prime * result + ((this.OR_ID == null) ? 0 : this.OR_ID.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (!(obj instanceof ControlCSV))
      return false;
    ControlCSV other = (ControlCSV) obj;
    if (this.CD_CODE == null) {
      if (other.CD_CODE != null)
        return false;
    } else if (!this.CD_CODE.equals(other.CD_CODE))
      return false;
    if (this.CD_LIBL == null) {
      if (other.CD_LIBL != null)
        return false;
    } else if (!this.CD_LIBL.equals(other.CD_LIBL))
      return false;
    if (this.CL_CRC == null) {
      if (other.CL_CRC != null)
        return false;
    } else if (!this.CL_CRC.equals(other.CL_CRC))
      return false;
    if (this.CL_ID == null) {
      if (other.CL_ID != null)
        return false;
    } else if (!this.CL_ID.equals(other.CL_ID))
      return false;
    if (this.CL_LIBC == null) {
      if (other.CL_LIBC != null)
        return false;
    } else if (!this.CL_LIBC.equals(other.CL_LIBC))
      return false;
    if (this.CL_NUM == null) {
      if (other.CL_NUM != null)
        return false;
    } else if (!this.CL_NUM.equals(other.CL_NUM))
      return false;
    if (this.OR_ID == null) {
      if (other.OR_ID != null)
        return false;
    } else if (!this.OR_ID.equals(other.OR_ID))
      return false;
    return true;
  }

  private String CL_CRC;

  private String CL_ID;

  private String OR_ID;

  private String CL_NUM;

  private String CL_LIBC;

  private String CD_CODE;

  private String CD_LIBL;

  /**
   * @return cL_CRC
   */
  public String getCL_CRC() {

    return this.CL_CRC;
  }

  /**
   * @param cL_CRC the cL_CRC to set
   */
  public void setCL_CRC(String cL_CRC) {

    this.CL_CRC = cL_CRC;
  }

  /**
   * @return cL_ID
   */
  public String getCL_ID() {

    return this.CL_ID;
  }

  /**
   * @param cL_ID the cL_ID to set
   */
  public void setCL_ID(String cL_ID) {

    this.CL_ID = cL_ID;
  }

  /**
   * @return oR_ID
   */
  public String getOR_ID() {

    return this.OR_ID;
  }

  /**
   * @param oR_ID the oR_ID to set
   */
  public void setOR_ID(String oR_ID) {

    this.OR_ID = oR_ID;
  }

  /**
   * @return cL_NUM
   */
  public String getCL_NUM() {

    return this.CL_NUM;
  }

  /**
   * @param cL_NUM the cL_NUM to set
   */
  public void setCL_NUM(String cL_NUM) {

    this.CL_NUM = cL_NUM;
  }

  /**
   * @return cL_LIBC
   */
  public String getCL_LIBC() {

    return this.CL_LIBC;
  }

  /**
   * @param cL_LIBC the cL_LIBC to set
   */
  public void setCL_LIBC(String cL_LIBC) {

    this.CL_LIBC = cL_LIBC;
  }

  /**
   * @return cD_CODE
   */
  public String getCD_CODE() {

    return this.CD_CODE;
  }

  /**
   * @param cD_CODE the cD_CODE to set
   */
  public void setCD_CODE(String cD_CODE) {

    this.CD_CODE = cD_CODE;
  }

  /**
   * @return cD_LIBL
   */
  public String getCD_LIBL() {

    return this.CD_LIBL;
  }

  /**
   * @param cD_LIBL the cD_LIBL to set
   */
  public void setCD_LIBL(String cD_LIBL) {

    this.CD_LIBL = cD_LIBL;
  }

}
