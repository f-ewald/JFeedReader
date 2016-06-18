package net.fewald.jfeedreader;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by fe on 16.05.16.
 */
public class UrlCodec implements Codec<URL> {
    public URL decode(BsonReader bsonReader, DecoderContext decoderContext) {
        URL url = null;
        try {
            url = new URL(bsonReader.readString());
        }
        catch (MalformedURLException exception) {

        }
        return url;
    }

    public void encode(BsonWriter bsonWriter, URL url, EncoderContext encoderContext) {
        bsonWriter.writeString(url.toString());
    }

    public Class<URL> getEncoderClass() {
        return URL.class;
    }
}
