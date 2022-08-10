public class zxj extends Thread{
    private ChopStick left, right;
    private int index;

    public zxj(ChopStick left, ChopStick right, int index){
        this.left = left;
        this.right = right;
        this.index = index;
    }

    public void run(){
        synchronized (left){
            try {
                Thread.sleep(2000);
            }catch (InterruptedException e ){
                e.printStackTrace();
            }
            System.out.println(index + "号哲学家等待右边筷子");
            synchronized (right){
                System.out.println(index + "哲学家拿到右边筷子,开饭");
            }
        }
    }
}



public static void main(String[]args){
        ChopStick chop1 = new ChopStick();
        ChopStick chop2 = new ChopStick();
        ChopStick chop3 = new ChopStick();
        ChopStick chop4 = new ChopStick();

        zxj zxjTread1 = new zxj(chop1, chop2, 1);
        zxj zxjTread2 = new zxj(chop2, chop3, 2);
        zxj zxjTread3 = new zxj(chop3, chop4, 3);
        zxj zxjTread4 = new zxj(chop4, chop1, 4);

        zxjTread1.start();
        zxjTread2.start();
        zxjTread3.start();
        zxjTread4.start();
        }