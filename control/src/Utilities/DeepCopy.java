package Utilities;

import com.ajexperience.utils.DeepCopyException;
import com.ajexperience.utils.DeepCopyUtil;

public final class DeepCopy<E>{
	
	public E deepcopy(E copyItem) throws DeepCopyException{
		DeepCopyUtil deepCopyUtil = new DeepCopyUtil();
		return deepCopyUtil.deepCopy(copyItem);
	}
}