package ai.ml.service;

import ai.ml.AiMlApplication;
import ai.ml.model.PerceptronNumber;
import ai.ml.model.PerceptronPixel;
import ai.ml.util.SizeConsts;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class Neiro {


    Logger logger = LoggerFactory.getLogger(AiMlApplication.class);

    List<PerceptronNumber> symbols = new ArrayList<>(SizeConsts.symbolsCount);
    List<PerceptronPixel> pixels = new ArrayList<>(SizeConsts.xSize * SizeConsts.ySize);

    private void createLists() {
        for (Integer i = 0; i < SizeConsts.symbolsCount; i++) {
            symbols.add(new PerceptronNumber(i.toString()));
        }
        for (Integer x = 0; x < SizeConsts.xSize; x++) {
            for (Integer y = 0; y < SizeConsts.ySize; y++) {
                pixels.add(new PerceptronPixel(x, y));
            }
        }
    }

}
