package com.run.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @desc: 资源接口，提供获取某资源（XxxResource）的输入流
 * @author: AruNi_Lu
 * @date: 2023/3/14
 */
public interface Resource {

    InputStream getInputStream() throws IOException;

}
