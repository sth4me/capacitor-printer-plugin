export interface PrinterPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
