import React, {useState, useEffect} from 'react';
import {
  NativeModules,
  NativeEventEmitter,
  View,
  Button,
  Text,
  Image,
} from 'react-native';

const {CameraModule} = NativeModules;
const cameraEventEmitter = new NativeEventEmitter(CameraModule);

const App: React.FC = () => {
  const [imagePath, setImagePath] = useState<string | null>(null);

  useEffect(() => {
    const subscription = cameraEventEmitter.addListener(
      'onImageCaptured',
      (path: string) => {
        setImagePath(path);
      },
    );

    return () => subscription.remove();
  }, []);

  const openCamera = () => {
    CameraModule.openCamera();
  };

  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <Button title="Open Camera" onPress={openCamera} />
      {imagePath ? (
        <View style={{marginTop: 20}}>
          <Text>Image Path:</Text>
          <Text>{imagePath}</Text>
          <Image
            source={{uri: `file://${imagePath}`}}
            style={{width: 200, height: 200, marginTop: 10}}
          />
        </View>
      ) : null}
    </View>
  );
};

export default App;
