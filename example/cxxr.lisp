(define numbers (cons (cons (quote 1) (quote 2)) (cons (quote 3) (quote 4))))
numbers

(define caar (lambda (l) (car (car l))))
(define cadr (lambda (l) (car (cdr l))))
(define cdar (lambda (l) (cdr (car l))))
(define cddr (lambda (l) (cdr (cdr l))))

(car numbers)
(cdr numbers)

(caar numbers)
(cadr numbers)
(cdar numbers)
(cddr numbers)
