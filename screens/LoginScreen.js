import React, { useState, useRef, useCallback, useMemo, useEffect } from 'react';
import {
  View,
  TextInput,
  TouchableOpacity,
  Button,
  Text,
  StyleSheet,
  Image,
} from 'react-native';
import SmartechBaseReact from 'smartech-base-react-native';
import BottomSheet, { BottomSheetView, BottomSheetBackdrop } from '@gorhom/bottom-sheet';

export default function LoginScreen({ navigation }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const bottomSheetRef = useRef(null);
  const snapPoints = useMemo(() => ['50%'], []);

  const handleOpen = () => {
    bottomSheetRef?.current?.snapToIndex(0);
  };

  const handleLogin = () => {
    if (email && password) {
      SmartechBaseReact.login(email);
      navigation.navigate('Home');
    } else {
      alert('Please fill in all fields');
    }
  };

  const renderBackdrop = useCallback(
    (props) => (
      <BottomSheetBackdrop
        {...props}
        disappearsOnIndex={-1} // backdrop hilang saat sheet tertutup
        appearsOnIndex={0}     // backdrop muncul saat index 0
        opacity={0.5}     
        nativeID={'hansel_ignore_container'}     // gelap transparan
      />
    ),
    []
  );

  return (
    <View style={styles.container} >
      <Image source={require('../assets/stylelogo.jpg')} style={styles.logo} />
      <Text style={styles.title}>Login</Text>

      <TextInput
        style={styles.input}
        placeholder="Email"
        placeholderTextColor="#888"
        onChangeText={setEmail}
        value={email}
      />
      <TextInput
        style={styles.input}
        placeholder="Password"
        placeholderTextColor="#888"
        secureTextEntry
        onChangeText={setPassword}
        value={password}
      />

      <TouchableOpacity style={styles.button} onPress={handleLogin}>
        <Text style={styles.buttonText}>Login</Text>
      </TouchableOpacity>

      <Text style={styles.forgotPassword}>Forgot Password?</Text>

      <View style={styles.signupContainer}>
        <Text style={styles.signupText}>Don't have an account? </Text>
        <TouchableOpacity onPress={() => navigation.navigate('SignUp')}>
          <Text style={styles.signupLink}>Sign Up</Text>
        </TouchableOpacity>

      </View>
      <Button title="Open Bottom Sheet" onPress={handleOpen} />
      <BottomSheet 
        ref={bottomSheetRef}
        index={-1}
        snapPoints={snapPoints}
        enablePanDownToClose
        backdropComponent={renderBackdrop}
        nativeID={'hansel_ignore_container'}

      >
        <BottomSheetView style={styles.sheetContainer}  nativeID={'hansel_ignore_container'} >
          <Text>Awesome 🎉</Text>
        </BottomSheetView>
      </BottomSheet>

    </View>


  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    paddingHorizontal: 32,
    backgroundColor: '#F5F5F5',
  },
  logo: {
    width: 120,
    height: 120,
    borderRadius: 100,
    alignSelf: 'center',
    marginBottom: 40,
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    marginBottom: 24,
    textAlign: 'center',
    color: '#333',
  },
  input: {
    height: 50,
    borderColor: '#DDDDDD',
    borderWidth: 1,
    borderRadius: 8,
    marginBottom: 16,
    paddingHorizontal: 16,
    backgroundColor: '#FFFFFF',
    fontSize: 16,
    color: '#333',
  },
  button: {
    backgroundColor: 'black',
    paddingVertical: 16,
    borderRadius: 8,
    alignItems: 'center',
    marginTop: 16,
  },
  buttonText: {
    color: '#FFFFFF',
    fontSize: 18,
    fontWeight: 'bold',
  },
  forgotPassword: {
    marginTop: 16,
    color: '#007BFF',
    textAlign: 'center',
  },
  signupContainer: {
    flexDirection: 'row',
    justifyContent: 'center',
    marginTop: 24,
  },
  signupText: {
    fontSize: 16,
    color: '#333',
  },
  signupLink: {
    fontSize: 16,
    color: '#007BFF',
    fontWeight: 'bold',
  },
  sheetContainer: {
    flex: 1,
    padding: 36,
    alignItems: 'center',
  },
});