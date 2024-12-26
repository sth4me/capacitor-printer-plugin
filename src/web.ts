import { WebPlugin } from '@capacitor/core';

import type { PrinterPluginPlugin } from './definitions';

export class PrinterPluginWeb extends WebPlugin implements PrinterPluginPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
