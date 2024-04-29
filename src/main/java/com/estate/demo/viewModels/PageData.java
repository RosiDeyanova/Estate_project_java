package com.estate.demo.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData<T> {
    public List<T> vmData;
    public Integer pageNumbers;
    public Integer currentPageNumber;
}
