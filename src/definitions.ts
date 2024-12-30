export interface PrinterPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  print(options: PrintOptions) : Promise<PrintResponse>;
  getPrinterList(): Promise<{ printerList: USBPrinterInfo[]; }>;
  connectToPrinter(options: { productId: number }): Promise<{ connected: boolean; }>;
}


export interface PrintOptions {
    printObject: string,
    lineFeed: number
}

export interface PrintResponse {
    message: string,
    content: string,
}

export interface USBPrinterInfo {
  productId?: number;
  productName: string;
  connected: boolean;
}

export interface USBPrinterLineEntry {
  lineText?: string;
  lineStyleList?: string[];
  lineCommandList?: string[];
}