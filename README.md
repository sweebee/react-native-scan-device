# react-native-scan-device

This module allows you to listen for scan events emitted by Honeywell NXXX scanners on Android devices.

## Getting started

`$ yarn add git+https://github.com/mediaburg/react-native-scan-device`

### Installation / Linking
Linking is done automatically in react-native 0.60+

cli-linking (only < 0.60)\
`$ react-native link react-native-scan-device`

## Usage
```javascript
import ScanDevice from 'react-native-scan-device';

// Create a receiver
const receiver = ScanDevice.onReceive((e) => {
    console.log(`Scanned Value: ${e.data}`);
});

// Remove the receiver
receiver.remove();


// Programmatically start scanning
ScanDevice.startScan();

// Programmatically stop the scanner (only needed if continuous scanning is active, which is not currently implemented)
ScanDevice.stopScan();
```
