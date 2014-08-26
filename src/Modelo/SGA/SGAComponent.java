package Modelo.SGA;

import java.util.Date;

/**
 *
 * @author Geykel
 */
public class SGAComponent {

    public SGAComponent() {
        hora = new Date();
        this.SGAFreeSpace = (double)0;
        this.SharedPool = (double)0;
        this.LargePool = (double)0;
        this.JavaPool = (double)0;
        this.SpFree = (double)0;
        this.LpFree = (double)0;
        this.JpFree = (double)0;
    }

    public Date getHora() {
        return hora;
    }

    public Double getSGAFreeSpace() {
        return SGAFreeSpace;
    }

    public Double getSharedPool() {
        return SharedPool;
    }

    public Double getLargePool() {
        return LargePool;
    }

    public Double getJavaPool() {
        return JavaPool;
    }

    public Double getSpFree() {
        return SpFree;
    }

    public Double getLpFree() {
        return LpFree;
    }

    public Double getJpFree() {
        return JpFree;
    }

    public void setSGAFreeSpace(Double SGAFreeSpace) {
        this.SGAFreeSpace = SGAFreeSpace;
    }

    public void setSharedPool(Double SharedPool) {
        this.SharedPool = SharedPool;
    }

    public void setLargePool(Double LargePool) {
        this.LargePool = LargePool;
    }

    public void setJavaPool(Double JavaPool) {
        this.JavaPool = JavaPool;
    }

    public void setSpFree(Double SpFree) {
        this.SpFree = SpFree;
    }

    public void setLpFree(Double LpFree) {
        this.LpFree = LpFree;
    }

    public void setJpFree(Double JpFree) {
        this.JpFree = JpFree;
    }

    public Double getMaxSGASize() {
        return MaxSGASize;
    }

    public void setMaxSGASize(Double MaxSGASize) {
        this.MaxSGASize = MaxSGASize;
    }
   
    private final Date hora;
    private Double MaxSGASize;
    private Double SGAFreeSpace;
    private Double SharedPool;
    private Double LargePool;
    private Double JavaPool;
    private Double SpFree;
    private Double LpFree;
    private Double JpFree;   
}
