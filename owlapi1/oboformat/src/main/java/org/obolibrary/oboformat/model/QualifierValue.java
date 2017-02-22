package org.obolibrary.oboformat.model;

/** qualifier value */
public class QualifierValue implements Comparable<QualifierValue> {

    protected String qualifier;
    protected Object value;

    /**
     * @param q
     *        qualifier
     * @param v
     *        value
     */
    public QualifierValue(String q, String v) {
        qualifier = q;
        value = v;
    }

    /** @return qualifier */
    public String getQualifier() {
        return qualifier;
    }

    /**
     * @param qualifier
     *        qualifier
     */
    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    /** @return value */
    public Object getValue() {
        return value;
    }

    /**
     * @param value
     *        value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" + qualifier + "=" + value + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (qualifier == null ? 0 : qualifier.hashCode());
        result = prime * result + (value == null ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof QualifierValue)) {
            return false;
        }
        QualifierValue other = (QualifierValue) obj;
        if (qualifier == null) {
            if (other.qualifier != null) {
                return false;
            }
        } else if (!qualifier.equals(other.qualifier)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(QualifierValue o) {
        if (o == null) {
            return 1;
        }
        // use toString representation
        return toString().compareTo(o.toString());
    }
}
