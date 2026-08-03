/*
 * StringUtil.js
 * String util functions
 * 
 * Copyright(C) Hatter Jiang
 */

(function($)
{
	$.StringUtil = {};
	
	// 判读字符串是否为空
	$.StringUtil.isEmpty = function(str)
    {
      return ((str == null) || (str == ""));
    },
    
    // 判读字符串是否不为空
    $.StringUtil.isNotEmpty = function(str)
    {
      return ((str != null) && (str != ""));
    },
	
    // 字符串为null,返回空字符串
	$.StringUtil.checkNull = function(str)
    {
      return (str == null)? "": str ;
    },
    
    // 去除左边空格
    $.StringUtil.trimLeft = function(str)
    {
      return (str == null)? "": str.toString().replace(/^\s*/, "");
    },
    
    // 去除右边空格
    $.StringUtil.trimRight = function(str)
    {
      return (str == null)? "": str.toString().replace(/\s*$/, "");
    },
    
    // 去除两边空格
    $.StringUtil.trim = function(str)
    {
      return this.trimLeft(this.trimRight(str));
    },
    
    // 判断是否为整数
    $.StringUtil.isString0To9 = function(str)
    {
      return (/^[0-9]*$/.test(str));
    },
    
    // 判断是否为小写
    $.StringUtil.isStringaToz = function(str)
    {
      return (/^[a-z]$/.test(str));
    },
    
    // 判断是否为大写
    $.StringUtil.isStringAToZ = function(str)
    {
      return (/^[A-Z]$/.test(str));
    },
    
    // ???
    $.StringUtil.isStringaTozOAToZ = function(str)
    {
      return (/^[a-z]*$/i.test(str));
    },
    
    // 判断substr是否包含于str的开头
    $.StringUtil.startWith = function(str, substr)
    {
      if (str != null)
      {
        if ((substr != null) && (substr.length != 0) && (substr.length <= str.length))
        {
          if (str.substring(0, substr.length) == substr)
          {
            return true;
          }
          return false;
        }
        else
        {
          if (substr.length > str.length)
          {
            return false;
          }
          else
          {
            return true;
          }
        }
      }
      else
      {
        if (substr == null)
        {
          return true;
        }
        else
        {
          return false;
        }
      }
    },
    
    // 判断substr是否包含于str的结尾
    $.StringUtil.endWith = function(str, substr)
    {
      if (str != null)
      {
        if ((substr != null) && (substr.length != 0) && (substr.length <= str.length))
        {
          if (str.substring(str.length - substr.length) == substr)
          {
            return true;
          }
          return false;
        }
        else
        {
          if (substr.length > str.length)
          {
            return false;
          }
          else
          {
            return true;
          }
        }
      }
      else
      {
        if (substr == null)
        {
          return true;
        }
        else
        {
          return false;
        }
      }
    },
    
    // 判断substrS包含于str开头, 并且substrE包含于str的结尾
    $.StringUtil.betweenWith = function(str, substrS, substrE)
    {
      return  (
                (this.startWith(str, substrS))
              &&
                (this.endWith(substrE))
              )? true: false;
    },
    
    // str字符中是否包含substr
    $.StringUtil.contains = function(str, substr)
    {
      if (((str == null) && (substr == null)) || (substr == null))
      {
        return true;
      }
      if (str == null)
      {
        return false;
      }
      if (str.indexOf(substr) >= 0)
      {
        return true;
      }
      return false;
    },
    
    // 替换字符串的特殊字符
    $.StringUtil.replaceAllHtmlTag = function(str)
    {
      if (str == null)
      {
        return "";
      }
      str = str.replace(/</g, "&lt;");
      str = str.replace(/>/g, "&gt;");
      str = str.replace(/ /g, "&nbsp;");
      str = str.replace(/\n/g, "<br/>");
      str = str.replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;");
      return str;
    },
    
    // 把strStr倒置
    $.StringUtil.reverse = function(strStr)
    {
      var lstStr = strStr.split(/.*?/);
      return lstStr.reverse().join("");
    },
    
    // 按intNum对strStr进行字符串复制
    $.StringUtil.repeat = function(strStr, intNum)
    {
      var lstStrout = [];
      for (var i = 0; i < intNum; i++)
      {
        lstStrout.push(strStr);
      }
      return lstStrout.join("");
    },
    
    // 把字符串大写转小写,小写转大写
    $.StringUtil.swapCharCase = function(strStr)
    {
      var lstChars = [];
      for (var i = 0; i < strStr.length; i++)
      {
        var chr = strStr.charAt(i);
        if (this.isStringAToZ(chr))
        {
          lstChars.push(chr.toLowerCase());
        }
        else if (this.isStringaToz(chr))
        {
          lstChars.push(chr.toUpperCase());
        }
        else
        {
          lstChars.push(chr);
        }
      }
      return lstChars.join("");
    },
    
    // 返回从iFr到iTo的Unicode代码
    $.StringUtil.getStringByCharCodeRange = function(iFr, iTo)
    {
      if (iFr > iTo)
      {
        throw new Exception("iFr cannot bigger than iTo.");
      }
      
      var r = [];
      
      for (var i = iFr; i<= iTo; i++)
      {
        r.push(String.fromCharCode(i));
      }
      
      return r.join("");
    },
    
    // 首字母小写
    $.StringUtil.lowerInitial = function(str)
    {
      if (str == null)
      {
        return null;
      }
      return str.substring(0, 1).toLowerCase() + str.substring(1);
    },
    
    // 首字母大写
    $.StringUtil.upperInitial = function(str)
    {
      if (str == null)
      {
        return null;
      }
      return str.substring(0, 1).toUpperCase() + str.substring(1);
    },
    
    // 取split之前的所有字符串
    $.StringUtil.beforeFirst = function(str, split, withSplit)
    {
      var i = str.indexOf(split);
      if (i < 0)
      {
        return null;
      }
      else
      {
        return str.substring(0, (i + (withSplit? split.length: 0)));
      }
    },
    
    // 取split之后的所有字符串
    $.StringUtil.afterFirst = function(str, split, withSplit)
    {
      var i = str.indexOf(split);
      if (i < 0)
      {
        return null;
      }
      else
      {
        return str.substring(i + (withSplit? 0: split.length));
      }
    },
    
    // ???
    $.StringUtil.beforeLast = function(str, split, withSplit)
    {
      var i = str.lastIndexOf(split);
      if (i < 0)
      {
        return null;
      }
      else
      {
        return str.substring(0, (i + (withSplit? split.length: 0)));
      }
    },
    
    // ???
    $.StringUtil.afterLast = function(str, split, withSplit)
    {
      var i = str.lastIndexOf(split);
      if (i < 0)
      {
        return null;
      }
      else
      {
        return str.substring(i + (withSplit? 0: split.length));
      }
    },
    
    // ???
    $.StringUtil.fillString = function(str, filllength, fillchar, beforeafterflag)
    {
      if (str == null)
      {
        str = "";
      }
      if (filllength == null)
      {
        filllength = 0;
      }
      if (fillchar == null)
      {
        fillchar = " ";
      }
      if (fillchar.length != 1)
      {
        throw new Exception("param `fillchar' should be char not string");
      }
      if (beforeafterflag == null)
      {
        beforeafterflag = true;
      }
      if (filllength <= 0)
      {
        return str;
      }
      var fillstring = "";
      var fillstringlength = filllength - str.length;
      for (var i = 0; i < fillstringlength; i++)
      {
        fillstring += fillchar;
      }
      if (beforeafterflag)
      {
        str = fillstring + str;
      }
      else
      {
        str += fillstring;
      }
      return str;
    },
    
    // ???
    $.StringUtil.formatIntFillZero = function(numbervalue, fillzerocount, beforeafterflag)
    {
      if ((numbervalue == null) || (isNaN(numbervalue)))
      {
        numbervalue = 0;
      }
      var minusflag = false;
      if ((beforeafterflag) && (numbervalue < 0))
      {
        minusflag = true;
      }
      if (beforeafterflag == null)
      {
        beforeafterflag = true;
      }
      numbervalue = numbervalue.toString();
      if (minusflag)
      {
        numbervalue = numbervalue.substring(1);
      }
      var needzerocount = fillzerocount - numbervalue.length;
      if (!(beforeafterflag))
      {
        needzerocount += 2;
      }
      if (minusflag)
      {
        needzerocount--;
      }
      var zeros = "";
      for (var i = 0; i < needzerocount; i++)
      {
        zeros += "0";
      }
      if (beforeafterflag)
      {
        numbervalue = zeros + numbervalue;
      }
      else
      {
        if (zeros.length > 0)
        {
          numbervalue = numbervalue.substring(2);
          numbervalue += zeros;
        }
        else
        {
          numbervalue = numbervalue.substring(2, fillzerocount + 2);
        }
      }
      if (minusflag)
      {
        numbervalue = "-" + numbervalue;
      }
      return numbervalue;
    },
    
 // ???
    $.StringUtil.getNumberFormat = function(patternbd, numbervalue)
    {
      if  (
            (typeof(numbervalue) != "number")
          &&
            (!(numbervalue instanceof Number))
          )
      {
        if  (
              (typeof(numbervalue) != "string")
            &&
              (!(numbervalue instanceof String))
            )
        {
          throw new Exception("invalid object pattern:" + "`" + numbervalue + "'");
        }
        else
        {
          if (!(/^(\d+(\.d*)?)|(\.\d*)?$/.test(numbervalue)))
          {
            throw new Exception("invalid string pattern:" + "`" + numbervalue + "'");
          }
        }
      }
      var beforedot = 0;
      var afterdot = 0;
      if (numbervalue == null)
      {
        numbervalue = 0;
      }
      if (this.contains(patternbd, "."))
      {
        var patternstrlist = patternbd.split(".");
        beforedot = parseInt(patternstrlist[0]);
        afterdot = parseInt(patternstrlist[1]);
      }
      else
      {
        beforedot = parseInt(patternbd);
      }
      if (isNaN(beforedot))
      {
        beforedot = 0;
      }
      if (isNaN(afterdot))
      {
        afterdot = 0;
      }
      var valuebeforedot = 0;
      var valueafterdot = 0;
      numbervalue = numbervalue.toString();
      if (this.contains(numbervalue, "."))
      {
        var numbervaluestrlist = numbervalue.split(".");
        valuebeforedot = parseInt(numbervaluestrlist[0]);
        valueafterdot = parseFloat("0." + numbervaluestrlist[1]);
      }
      else
      {
        valuebeforedot = parseInt(numbervalue);
      }
      if (isNaN(valuebeforedot))
      {
        valuebeforedot = 0;
      }
      if (isNaN(valueafterdot))
      {
        valueafterdot = 0;
      }
      var retvalue = "";
      retvalue += this.formatIntFillZero(valuebeforedot, beforedot, true);
      if (afterdot != 0)
      {
        retvalue += ".";
        retvalue += this.formatIntFillZero(valueafterdot, afterdot, false);
      }
      return retvalue;
    }
  
})(jQuery);

