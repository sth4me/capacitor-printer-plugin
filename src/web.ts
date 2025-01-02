import { WebPlugin } from '@capacitor/core';

import type { PrinterPlugin, PrintOptions, PrintResponse, USBPrinterInfo, CharsetEncoding } from './definitions';

export class PrinterPluginWeb extends WebPlugin implements PrinterPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }


  async print(options: PrintOptions) : Promise<PrintResponse> {
      console.debug('print options: ', options)
      return Promise.resolve({
        content: options.printObject,
        message: 'Web version of SumUp not available.'
      })
  }

  async getPrinterList() : Promise<{ printerList: USBPrinterInfo[]; }> {
      console.debug('getPrinterList')
      return Promise.resolve({
        printerList: [],
        message: 'Web version of SumUp not available.'
      })
  }

  async connectToPrinter(options: { productId: number }) : Promise<{ connected: boolean; }> {
      console.debug('connectToPrinter',options)
      return Promise.resolve({
        connected: false,
        message: 'Web version of SumUp not available.'
      })
  }

  async getEncoding() : Promise<{ encoding: CharsetEncoding; }> {
      console.debug('getEncoding')
      return Promise.resolve({
        encoding: {
          charsetName: '',
          charsetCommond: new Uint8Array(0)
        },
        message: 'Web version of SumUp not available.'
      })
  }
}
