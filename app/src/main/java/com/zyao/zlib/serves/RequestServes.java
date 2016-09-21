/**
 * Title: RequestServes.java
 * Package: com.zyao.zlib.serves
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/21
 */
package com.zyao.zlib.serves;

import okhttp3.ResponseBody;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Interface: RequestServes
 * Description: TODO 功能描述...
 * Author: Zyao89
 * Time: 2016/9/21 10:35
 */
public interface RequestServes
{
    @POST("/android")
    Observable<ResponseBody> getContext ();
}
