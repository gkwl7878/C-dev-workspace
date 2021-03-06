package day1129;

/**
 *private로 된 배열을 public method 로 내보내면 외부에서 private 배열 값을수정할 수 있게 된다.
 *(안전한 코딩 기법 - 배열을 복사해서 리턴)
 * @author owner
 */
public class SecureArray {
	private int[] arr= {1,2,3,4,5};
	
	public int[] getArr() {
		//외부에서 값변경 했을 때 원봉 배열의 값이 변경되지 않도록 배열을 복사하여 리턴한다.
		int[] temp=new int[arr.length];
		for(int i=0; i<arr.length; i++) {
			temp[i]=arr[i];
		}//end for
		
		return temp;
	}//getArr
	
	public static void main(String[] args) {

	}

}
