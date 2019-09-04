package com.imain.jacksondemo.jsoncomponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * author Songrui.Liu
 * date 2019/9/318:13
 */
@JsonComponent
public class UserJsonDeserializer extends JsonDeserializer<User> {

    @Override
    public User deserialize(JsonParser jsonParser,
                            DeserializationContext deserializationContext) throws IOException,
            JsonProcessingException {

        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);
        TextNode favoriteColor
                = (TextNode) treeNode.get("favoriteColor");
        return new User().setFavoriteColor(new Color().setRed(1).setGreen(2).setBlue(3));
    }
}
