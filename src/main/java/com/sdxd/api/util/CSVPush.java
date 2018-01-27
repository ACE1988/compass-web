package com.sdxd.api.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by matteo on 2017/8/8.
 */
public class CSVPush {

    private InputStream inputStream;

    public CSVPush(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public List<String> parse() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        Boolean isFirst = true;
        List<String> userIdList = new ArrayList<>();
        String line;
        do {
            line = br.readLine();
            if(line == null) break;
            if (StringUtils.isBlank(line)) continue;
            if (isFirst) {
                isFirst = false;
                continue;
            }
            userIdList.add(line.split(",")[0]);
        } while (true);

        return userIdList;
    }
}
