/**
 * Title: Configs.java
 * Package: com.zyao.config
 * Description: 本内容仅Zyao89持有，转载请标注。
 * Author: Zyao89
 * Date: 2016/9/21
 */
package com.zyao.config;

import com.zyao.zutils.Z;

import java.io.File;

/**
 * Interface: Configs
 * Description: Debug配置
 * Author: Zyao89
 * Time: 2016/9/21 14:27
 */
public interface Configs
{
    boolean DEBUG = true;

    /** ================= PATH ==================== **/

    String PATH_DATA = Z.app().getCacheDir().getAbsolutePath() + File.separator + "data";

    String PATH_CACHE = PATH_DATA + "/NetCache";
}
