package com.yj.generalbean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 29029
 * @Version 1.0
 * @Time 9:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadBean {
    private String errno;

    private String[] data;
}
