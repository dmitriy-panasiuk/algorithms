public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    public Picture picture() {
        return this.picture;
    }

    public int width() {
        return this.picture.width();
    }

    public int height() {
        return this.picture.height();
    }

    public double energy(int x, int y) {
        throw new UnsupportedOperationException();
    }

    public int[] findHorizontalSeam() {
        throw new UnsupportedOperationException();
    }

    public int[] findVerticalSeam() {
        throw new UnsupportedOperationException();
    }

    public void removeHorizontalSeam(int[] seam) {
        throw new UnsupportedOperationException();
    }

    public void removeVerticalSeam(int[] seam) {
        throw new UnsupportedOperationException();
    }
}
