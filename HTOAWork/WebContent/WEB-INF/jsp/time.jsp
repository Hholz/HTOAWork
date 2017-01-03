<?xml version="1.0" encoding="utf-8"?>
<mx:Module xmlns:mx="http://www.adobe.com/2006/mxml" layout="absolute" width="165" height="25">
    <mx:TextInput x="0" y="0" width="100" height="25" editable="{initeditable}" id="returnTime" text="{initvalue}"/>
    <mx:Button x="102" y="2" icon="@Embed(source='mx/controls/DateChooser.png')" fontWeight="normal" width="22" click="open(event)" height="22"/>
    <mx:Script>
        <![CDATA[
            import mx.containers.HBox;
            import mx.containers.TitleWindow;
            import mx.controls.Alert;
            import mx.controls.Button;
            import mx.controls.Label;
            import mx.controls.NumericStepper;
            import mx.controls.Text;
            import mx.controls.TileList;
            import mx.core.IFlexDisplayObject;
            import mx.events.CloseEvent;
            import mx.events.ListEvent;
            import mx.events.NumericStepperEvent;
            import mx.managers.PopUpManager;
            
            [Bindable]
            private var initvalue: String;
            [Bindable]
            private var initeditable:Boolean;
            
            public var resultTime:Text;
            private var m_width:Number = 168;
            
            private var pop:Object;
            private var isOpen:Boolean = false;
            private var nowTime:Date = new Date();
            
            private function open(event:MouseEvent):void{
                resultTime = new Text();
                if(!isOpen)
                {
                    pop = PopUpManager.createPopUp(this, TitleWindow, false); 
                    pop.showCloseButton = true;
                    pop.addEventListener(CloseEvent.CLOSE,closeHandler);
                    pop.move(event.stageX+event.target.width-event.localX+5,event.stageY-event.localY);
                    //pop.title = nowTime.fullYear.toString() + " 年 " + (nowTime.month+1) + " 月";
                    pop.title = "日期选择";
                    pop.setStyle("fontSize",12);
                    
                    
                    var yearChangeHandler:Function = function(evt:NumericStepperEvent):void{
                        nowTime.setFullYear(evt.value,nowTime.month,nowTime.date);
                        PopUpManager.removePopUp(pop as IFlexDisplayObject);
                        isOpen = false;
                        open(event);
                    }
                    
                    var monthChangeHandler:Function = function(evt:NumericStepperEvent):void{
                        nowTime.setFullYear(nowTime.fullYear,evt.value-1,nowTime.date);
                        PopUpManager.removePopUp(pop as IFlexDisplayObject);
                        isOpen = false;
                        open(event);
                    }
                    var testHandler:Function = function(event:ListEvent):void{
                        resultTime.text = nowTime.fullYear + "-" + (nowTime.month+1);
                        returnTime.text =  resultTime.text;
                    }
                    var onSubBtn_Click:Function = function(event:MouseEvent):void
                    {
                        resultTime.text = nowTime.fullYear + "-" + (nowTime.month+1);
                        returnTime.text =  resultTime.text;
                        
                        PopUpManager.removePopUp(pop as IFlexDisplayObject);
                        isOpen = false;
                    
                    }
                    
                    var hBoxTop:HBox = new HBox();
                    hBoxTop.width = m_width;
                    var year:NumericStepper = new NumericStepper();
                    year.setStyle("fontSize",10);
                    year.stepSize = 1;
                    year.minimum = 1999;
                    year.maximum = 2999;
                    year.width = 60;
                    year.height = 20;
                    year.value = nowTime.fullYear;
                    year.addEventListener(NumericStepperEvent.CHANGE,yearChangeHandler);
                    
                    var y_label:Label = new Label();
                    y_label.text = "年";
                    y_label.setStyle("textAlign","center");
                    y_label.width = 15;
                    
                    var month:NumericStepper = new NumericStepper();
                    month.setStyle("fontSize",10);
                    month.stepSize = 1;
                    month.minimum = 0;
                    month.maximum = 13;
                    month.width = 50;
                    month.height = 20;
                    month.value = nowTime.month+1;
                    month.addEventListener(NumericStepperEvent.CHANGE,monthChangeHandler);
                    
                    var m_label:Label = new Label();
                    m_label.text = "月";
                    m_label.setStyle("textAlign","center");
                    m_label.width = 15;
                    
                    hBoxTop.addChild(year);
                    hBoxTop.addChild(y_label);
                    hBoxTop.addChild(month);
                    hBoxTop.addChild(m_label);
                    
                    
                    pop.addChild(hBoxTop);
                    var subBtn:Button = new Button();
                    subBtn.addEventListener(MouseEvent.CLICK,onSubBtn_Click);
                    subBtn.label = "确定";
                    pop.addChild(subBtn);
                    isOpen = true;
                }
             else{
                    PopUpManager.removePopUp(pop as IFlexDisplayObject);
                    isOpen = false;
                }
            }
            
            private function closeHandler(event:CloseEvent):void{
                PopUpManager.removePopUp(event.target as IFlexDisplayObject);
                isOpen = false;
            }
            
            public function set text(str:String):void{
                initvalue = str ;
            }
            public function get text():String{
                return returnTime.text ;
            }
            
            public function set editable(str:Boolean):void{
                initeditable = str;
            }
            public function get editable(): Boolean{
                return returnTime.editable;
            }
        ]]>
    </mx:Script>
</mx:Module>